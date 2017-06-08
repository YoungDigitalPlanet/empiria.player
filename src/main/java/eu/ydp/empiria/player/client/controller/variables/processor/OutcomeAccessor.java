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
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;
import static java.lang.Integer.parseInt;

@Singleton
public class OutcomeAccessor {

    @Inject
    private SessionDataSupplier sessionDataSupplier;
    @Inject
    private FlowDataSupplier flowDataSupplier;
    @Inject
    private OutcomesResultCalculator resultCalculator;

    public int getCurrentPageTodo() {
        return getVariableAsInt(TODO);
    }

    public int getCurrentPageDone() {
        return getVariableAsInt(DONE);
    }

    public int getCurrentPageErrors() {
        return getVariableAsInt(ERRORS);
    }

    public int getCurrentPageMistakes() {
        return getVariableAsInt(MISTAKES);
    }

    public LastMistaken getCurrentPageLastMistaken() {
        String lastmistaken = getVariableAsString(LASTMISTAKEN);
        return LastMistaken.valueOf(lastmistaken.toUpperCase());
    }

    public int getAssessmentTodo() {
        return getAssessmentValueInt(TODO);
    }

    public int getAssessmentDone() {
        return getAssessmentValueInt(DONE);
    }

    public int getAssessmentErrors() {
        return getAssessmentValueInt(ERRORS);
    }

    public int getAssessmentMistakes() {
        return getAssessmentValueInt(MISTAKES);
    }

    public int getAssessmentResult() {
        int todo = getAssessmentTodo();
        int done = getAssessmentDone();
        return resultCalculator.calculateResult(todo, done);
    }

    private int getAssessmentValueInt(VariableName identifier) {
        String valueString = getAssessmentVariableValueString(identifier);
        return parseInt(valueString);
    }

    private String getAssessmentVariableValueString(VariableName identifier) {
        AssessmentSessionDataSocket assessmentSessionDataSocket = sessionDataSupplier.getAssessmentSessionDataSocket();
        VariableProviderSocket variableProviderSocket = assessmentSessionDataSocket.getVariableProviderSocket();
        Variable variable = variableProviderSocket.getVariableValue(identifier.toString());
        return variable.getValuesShort();
    }

    @SuppressWarnings("unchecked")
    public boolean isLastActionSelection() {
        VariableProviderSocket variableProviderSocket = getVariableProvider();
        if (variableProviderSocket instanceof VariablePossessorBase<?>) {
            VariablePossessorBase<Variable> variablePossessor = (VariablePossessorBase<Variable>) variableProviderSocket;
            return variablePossessor.isLastAnswerSelectAction();
        }
        return false;
    }

    private int getVariableAsInt(VariableName variable) {
        String todoString = getVariableAsString(variable);
        return Integer.valueOf(todoString);
    }

    private String getVariableAsString(VariableName variable) {
        VariableProviderSocket variableProvider = getVariableProvider();
        Variable variableValue = variableProvider.getVariableValue(variable.toString());
        String todoString = variableValue.getValuesShort();
        return todoString;
    }

    private VariableProviderSocket getVariableProvider() {
        int currentPageIndex = flowDataSupplier.getCurrentPageIndex();
        VariableProviderSocket variableProvider = sessionDataSupplier.getItemSessionDataSocket(currentPageIndex).getVariableProviderSocket();
        return variableProvider;
    }

}
