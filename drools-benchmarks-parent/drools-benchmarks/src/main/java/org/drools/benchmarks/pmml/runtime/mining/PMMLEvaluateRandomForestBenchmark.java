/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License. 
 */

package org.drools.benchmarks.pmml.runtime.mining;

import org.drools.benchmarks.common.AbstractBenchmark;
import org.drools.benchmarks.common.ProviderException;
import org.kie.api.pmml.PMML4Result;
import org.kie.pmml.api.runtime.PMMLRuntime;
import org.kie.pmml.api.runtime.PMMLRuntimeContext;
import org.kie.pmml.evaluator.core.service.PMMLRuntimeInternalImpl;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.drools.benchmarks.pmml.util.PMMLUtil.*;

@State(Scope.Benchmark)
@Warmup(iterations = 300)
@Measurement(iterations = 50)
public class PMMLEvaluateRandomForestBenchmark extends AbstractBenchmark {

    public static final String MODEL_NAME = "RandomForest";
    public static final String FILE_NAME_NO_SUFFIX = "RandomForest";

    public static final String FILE_NAME = FILE_NAME_NO_SUFFIX + ".pmml";
    public static final String FILE_PATH = "pmml/" + FILE_NAME;

    private PMMLRuntime pmmlRuntime;

    private static final Map<String, Object> INPUT_DATA;

    private static final PMMLRuntimeContext pmmlRuntimeContext;

    static {
        // Retrieve pmmlFile
        File pmmlFile = getPMMLFile(FILE_PATH, FILE_NAME);

        // Set input data
        INPUT_DATA = new HashMap<>();
        INPUT_DATA.put("Age", 40.83);
        INPUT_DATA.put("MonthlySalary", 3.5);
        INPUT_DATA.put("TotalAsset", 0.04);
        INPUT_DATA.put("TotalRequired", 10.04);
        INPUT_DATA.put("NumberInstallments", 93.2);

        // Instantiate pmmlRuntimeContext
        pmmlRuntimeContext = getPMMLRuntimeContext(INPUT_DATA, MODEL_NAME, FILE_NAME_NO_SUFFIX, pmmlFile);
    }

    @Setup
    public void setupResource() throws IOException {
        pmmlRuntime = new PMMLRuntimeInternalImpl();
    }

    @Override
    public void setup() throws ProviderException {
        // noop
    }

    @Benchmark
    public PMML4Result evaluatePrediction() {
        return evaluate(MODEL_NAME, pmmlRuntime, pmmlRuntimeContext);
    }
}
