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

package org.drools.benchmarks.turtle.runtime;

import java.util.ArrayList;
import java.util.List;
import org.drools.benchmarks.turtle.runtime.generator.BasicUpdateFactsInWmFactsGenerator;
import org.drools.benchmarks.turtle.runtime.generator.GeneratorConfiguration;
import org.drools.benchmarks.turtle.runtime.generator.PlaceHolder;
import org.kie.api.runtime.KieSession;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

public class UpdateFactsInWmExpertBenchmark extends AbstractSimpleRuntimeBenchmark {

    @Param({"400000"})
    int nrOfFacts;

    @Override
    protected void addResources() {
        addClassPathResource("turtle/expert-basic-update-facts-in-wm-1000.drl");
    }

    @Override
    protected void addFactsGenerators() {
        addFactsGenerator(new BasicUpdateFactsInWmFactsGenerator(getGeneratorConfiguration()),nrOfFacts);
    }

    @Benchmark
    public KieSession timeFactsInsertionAndRulesFiring() {
        return insertFactsAndFireAllRules();
    }

    private GeneratorConfiguration getGeneratorConfiguration() {
        final GeneratorConfiguration generatorConfiguration = new GeneratorConfiguration(
                1000, 10, 1.0);
        generatorConfiguration.setPlaceHolders(getPlaceholders());
        return generatorConfiguration;
    }

    private List<PlaceHolder> getPlaceholders() {
        final List<PlaceHolder> placeHolders = new ArrayList<>();
        placeHolders.add(new PlaceHolder("balance", 100, 10000));
        return placeHolders;
    }
}
