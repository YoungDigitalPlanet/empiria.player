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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

import java.util.Map;
import java.util.Set;

public class VariableProviderSocketMock implements VariableProviderSocket {

    private final Map<String, Variable> variables = Maps.newHashMap();

    private final OutcomeCreator outcomeCreator = new OutcomeCreator();

    private int todo;

    private int done;

    private int errors;

    private int checks;

    private int mistakes;

    private int reset;

    private int showAnswers;

    public void setResultValues(int todo, int done, int errors) {
        this.todo = todo;
        this.done = done;
        this.errors = errors;
        addResultVariables();
    }

    private void addResultVariables() {
        variables.put(VariableName.TODO.toString(), outcomeCreator.createTodoOutcome(todo));
        variables.put(VariableName.DONE.toString(), outcomeCreator.createDoneOutcome(done));
        variables.put(VariableName.ERRORS.toString(), outcomeCreator.createErrorsOutcome(errors));
    }

    public void setHintValues(int checks, int mistakes, int reset, int showAnswers) {
        this.checks = checks;
        this.mistakes = mistakes;
        this.reset = reset;
        this.showAnswers = showAnswers;
        addHintVariables();
    }

    private void addHintVariables() {
        variables.put(FlowActivityVariablesProcessor.CHECKS, outcomeCreator.createChecksOutcome(checks));
        variables.put(VariableName.MISTAKES.toString(), outcomeCreator.createMistakesOutcome(mistakes));
        variables.put(FlowActivityVariablesProcessor.RESET, outcomeCreator.createResetOutcome(reset));
        variables.put(FlowActivityVariablesProcessor.SHOW_ANSWERS, outcomeCreator.createShowAnswersOutcome(showAnswers));
    }

    @Override
    public Set<String> getVariableIdentifiers() {
        return null;
    }

    @Override
    public Variable getVariableValue(String identifier) {
        return variables.get(identifier);
    }

}
