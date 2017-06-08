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

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.*;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessingResultsToOutcomeMapConverter {

    private static final String TODO = "TODO";
    private static final String DONE = "DONE";
    private static final String ERRORS = "ERRORS";
    private static final String MISTAKES = "MISTAKES";
    private static final String LASTMISTAKEN = "LASTMISTAKEN";
    private static final String LASTCHANGE = "LASTCHANGE";

    private final ItemOutcomeStorageImpl outcomeManager;
    private final AnswersChangesFormater answersChangesFormater;

    @Inject
    public ProcessingResultsToOutcomeMapConverter(@Assisted ItemOutcomeStorageImpl outcomeManager, AnswersChangesFormater answersChangesFormater) {
        this.outcomeManager = outcomeManager;
        this.answersChangesFormater = answersChangesFormater;
    }

    public void updateOutcomeMapWithGlobalVariables(GlobalVariables globalVariables) {
        insertVariable(TODO, globalVariables.getTodo());
        insertVariable(DONE, globalVariables.getDone());
        insertVariable(ERRORS, globalVariables.getErrors());
        insertVariable(MISTAKES, globalVariables.getMistakes());
        insertVariable(LASTMISTAKEN, globalVariables.getLastMistaken().toString());
    }

    public void updateOutcomeMapByModulesProcessingResults(ModulesProcessingResults modulesProcessingResults) {
        Set<String> idsOfProcessedResponses = modulesProcessingResults.getIdsOfProcessedResponses();
        for (String responseId : idsOfProcessedResponses) {
            DtoModuleProcessingResult moduleProcessingResult = modulesProcessingResults.getProcessingResultsForResponseId(responseId);
            insertVariablesToMap(responseId, moduleProcessingResult);
        }
    }

    private void insertVariablesToMap(String responseId, DtoModuleProcessingResult moduleProcessingResult) {
        insertConstantVariables(responseId, moduleProcessingResult.getConstantVariables());
        insertUserInteractionVariables(responseId, moduleProcessingResult.getUserInteractionVariables());
        insertGeneralVariables(responseId, moduleProcessingResult.getGeneralVariables());
    }

    private void insertConstantVariables(String responseId, ConstantVariables constantVariables) {
        int todoValue = constantVariables.getTodo();
        insertModuleVariable(TODO, responseId, todoValue);
    }

    private void insertUserInteractionVariables(String responseId, UserInteractionVariables userInteractionVariables) {
        int mistakesValue = userInteractionVariables.getMistakes();
        insertModuleVariable(MISTAKES, responseId, mistakesValue);

        LastMistaken lastMistakenValue = userInteractionVariables.getLastmistaken();
        insertModuleVariable(LASTMISTAKEN, responseId, lastMistakenValue.toString());

        LastAnswersChanges lastAnswerChanges = userInteractionVariables.getLastAnswerChanges();
        List<String> lastchanges = answersChangesFormater.formatLastAnswerChanges(lastAnswerChanges);
        String lastchangeIdentifier = buildModuleVariableIdentifier(LASTCHANGE, responseId);
        insertVariable(lastchangeIdentifier, lastchanges);
    }

    private void insertGeneralVariables(String responseId, GeneralVariables generalVariables) {
        int errorValue = generalVariables.getErrors();
        insertModuleVariable(ERRORS, responseId, errorValue);

        int doneValue = generalVariables.getDone();
        insertModuleVariable(DONE, responseId, doneValue);
    }

    private void insertModuleVariable(String variableName, String moduleId, int value) {
        insertModuleVariable(variableName, moduleId, String.valueOf(value));
    }

    private void insertModuleVariable(String variableName, String moduleId, boolean value) {
        int valueConvertedToInt = convertBooleanToInt(value);
        insertModuleVariable(variableName, moduleId, valueConvertedToInt);
    }

    private int convertBooleanToInt(boolean value) {
        int valueConvertedToInt;
        if (value) {
            valueConvertedToInt = 1;
        } else {
            valueConvertedToInt = 0;
        }
        return valueConvertedToInt;
    }

    private void insertModuleVariable(String variableName, String moduleId, String value) {
        String variableIdentifier = buildModuleVariableIdentifier(variableName, moduleId);
        insertVariable(variableIdentifier, value);
    }

    private String buildModuleVariableIdentifier(String variableName, String moduleId) {
        String variableIdentifier = moduleId + "-" + variableName;
        return variableIdentifier;
    }

    private void insertVariable(String identifier, int value) {
        insertVariable(identifier, String.valueOf(value));
    }

    private void insertVariable(String identifier, String value) {
        Outcome outcome = new Outcome(identifier, Cardinality.SINGLE, "" + value);
        outcomeManager.putVariable(identifier, outcome);
    }

    private void insertVariable(String identifier, List<String> values) {
        Outcome outcome = new Outcome(identifier, Cardinality.MULTIPLE);
        outcome.values = values;
        outcomeManager.putVariable(identifier, outcome);
    }
}
