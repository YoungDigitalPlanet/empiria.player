/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.variables.processor.results;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ModulesProcessingResults {

    private final InitialProcessingResultFactory processingResultFactory;
    private final Map<String, DtoModuleProcessingResult> responseIdToProcessingResults = Maps.newHashMap();

    @Inject
    public ModulesProcessingResults(InitialProcessingResultFactory processingResultFactory) {
        this.processingResultFactory = processingResultFactory;
    }

    public DtoModuleProcessingResult getProcessingResultsForResponseId(String responseId) {
        DtoModuleProcessingResult processingResult = getOrCreateProcessingResultForResponseId(responseId);
        return processingResult;
    }

    public Map<String, DtoModuleProcessingResult> getMapOfProcessingResults() {
        return Collections.unmodifiableMap(responseIdToProcessingResults);
    }

    public Set<String> getIdsOfProcessedResponses() {
        return responseIdToProcessingResults.keySet();
    }

    private DtoModuleProcessingResult getOrCreateProcessingResultForResponseId(String responseId) {
        if (responseIdToProcessingResults.containsKey(responseId)) {
            return responseIdToProcessingResults.get(responseId);
        } else {
            DtoModuleProcessingResult newProcessingResults = processingResultFactory.createProcessingResultWithInitialValues();
            responseIdToProcessingResults.put(responseId, newProcessingResults);
            return newProcessingResults;
        }
    }
}
