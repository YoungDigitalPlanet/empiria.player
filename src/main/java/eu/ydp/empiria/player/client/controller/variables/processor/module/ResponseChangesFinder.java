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

package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

import java.util.ArrayList;
import java.util.List;

public class ResponseChangesFinder {

    private final ResponseDifferenceFinder responseDifferenceFinder;
    private final OrderedResponseChangesFinder orderedResponseChangesFinder;

    @Inject
    public ResponseChangesFinder(ResponseDifferenceFinder responseDifferenceFinder, OrderedResponseChangesFinder ordererResponseChangesFinder) {
        this.responseDifferenceFinder = responseDifferenceFinder;
        this.orderedResponseChangesFinder = ordererResponseChangesFinder;
    }

    public List<DtoProcessedResponse> findChangesOfAnswers(ModulesProcessingResults processingResults, VariableManager<Response> responseManager) {
        List<DtoProcessedResponse> changedResponses = new ArrayList<DtoProcessedResponse>();

        for (String responseIdentifier : responseManager.getVariableIdentifiers()) {
            Response response = responseManager.getVariable(responseIdentifier);
            DtoModuleProcessingResult previousProcessingResult = processingResults.getProcessingResultsForResponseId(responseIdentifier);

            DtoProcessedResponse changedResponse = getChangedResponseForResponseId(response, previousProcessingResult);
            changedResponses.add(changedResponse);
        }

        return changedResponses;
    }

    private DtoProcessedResponse getChangedResponseForResponseId(Response response, DtoModuleProcessingResult previousProcessingResult) {
        List<String> currentAnswers = getAnswersOrEmptyList(response);
        List<String> previousAnswers = getPreviousAnswers(previousProcessingResult);

        LastAnswersChanges changesOfAnswers = getChangesOfAnswers(response, currentAnswers, previousAnswers);

        UserInteractionVariables userInteractionVariables = previousProcessingResult.getUserInteractionVariables();
        userInteractionVariables.setLastAnswerChanges(changesOfAnswers);

        DtoProcessedResponse changedResponse = new DtoProcessedResponse(response, previousProcessingResult, changesOfAnswers);
        return changedResponse;
    }

    private LastAnswersChanges getChangesOfAnswers(Response response, List<String> currentAnswers, List<String> previousAnswers) {
        LastAnswersChanges changesOfAnswers;
        if (response.cardinality == Cardinality.ORDERED) {
            changesOfAnswers = orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
        } else {
            changesOfAnswers = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
        }
        return changesOfAnswers;
    }

    private List<String> getPreviousAnswers(DtoModuleProcessingResult previousProcessingResult) {
        List<String> previousAnswers = previousProcessingResult.getGeneralVariables().getAnswers();
        return previousAnswers;
    }

    private List<String> getAnswersOrEmptyList(Response response) {
        return Objects.firstNonNull(response.values, new ArrayList<String>());
    }
}
