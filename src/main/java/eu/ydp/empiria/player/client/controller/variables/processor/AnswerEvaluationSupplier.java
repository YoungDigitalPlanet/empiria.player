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

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

import java.util.List;
import java.util.logging.Logger;

/**
 * inject in {@link PageScoped}
 */
public class AnswerEvaluationSupplier {

    private static final Logger LOGGER = Logger.getLogger(VariableProcessingAdapter.class.getName());

    private ModulesProcessingResults modulesProcessingResults;

    public void updateModulesProcessingResults(ModulesProcessingResults modulesProcessingResults) {
        this.modulesProcessingResults = modulesProcessingResults;
    }

    public List<Boolean> evaluateAnswer(Response response) {
        if (modulesProcessingResults == null) {
            return handleInvalidEvaluationRequest();
        }
        return doEvaluation(response);
    }

    private List<Boolean> handleInvalidEvaluationRequest() {
        String message = "Cannot evaluate answers before first variables processing! Returning empty answerEvaluations list";
        LOGGER.warning(message);
        return Lists.newArrayList();
    }

    private List<Boolean> doEvaluation(Response response) {
        DtoModuleProcessingResult processingResult = modulesProcessingResults.getProcessingResultsForResponseId(response.getID());
        GeneralVariables generalVariables = processingResult.getGeneralVariables();
        List<Boolean> answersEvaluation = generalVariables.getAnswersEvaluation();
        return answersEvaluation;
    }
}
