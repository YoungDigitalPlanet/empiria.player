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

package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.OutcomeController;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

import java.util.Map;

public class MistakesInitializer {

    private final ModulesProcessingResults processingResults;
    private final OutcomeController outcomeController;

    @Inject
    public MistakesInitializer(@PageScoped ModulesProcessingResults processingResults, OutcomeController outcomeController) {
        this.processingResults = processingResults;
        this.outcomeController = outcomeController;
    }

    public void initialize(ItemOutcomeStorageImpl outcomeManager) {
        Map<String, Integer> mistakeResponses = outcomeController.getAllMistakes(outcomeManager);
        updateMistakes(mistakeResponses);
    }

    private void updateMistakes(Map<String, Integer> mistakeResponses) {
        for (String key : mistakeResponses.keySet()) {
            Integer mistakesNumber = mistakeResponses.get(key);

            DtoModuleProcessingResult moduleProcessingResult = processingResults.getProcessingResultsForResponseId(key);
            UserInteractionVariables userInteractionVariables = moduleProcessingResult.getUserInteractionVariables();
            userInteractionVariables.setMistakes(mistakesNumber);
        }
    }
}
