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

package eu.ydp.empiria.player.client.controller.feedback;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;

import static eu.ydp.empiria.player.client.controller.variables.objects.Cardinality.SINGLE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.*;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;

public class OutcomeCreator {

    private final String moduleId;

    public OutcomeCreator() {
        this("");
    }

    public OutcomeCreator(String moduleId) {
        this.moduleId = moduleId;
    }

    public Outcome createOutcome(String name, Cardinality cardinality, String value) {
        String identifier = moduleId + "-" + name;
        return new Outcome(identifier, cardinality, value);
    }

    public Outcome createDoneOutcome(int done) {
        return createOutcome(DONE.toString(), SINGLE, String.valueOf(done));
    }

    public Outcome createTodoOutcome(int todo) {
        return createOutcome(TODO.toString(), SINGLE, String.valueOf(todo));
    }

    public Outcome createLastChangeOutcome(String identifier) {
        return createOutcome(LASTCHANGE.toString(), SINGLE, identifier);
    }

    public Outcome createLastMistakenOutcome(LastMistaken lastMistaken) {
        return createOutcome(LASTMISTAKEN.toString(), SINGLE, lastMistaken.toString());
    }

    public Outcome createMistakesOutcome(int mistakesNum) {
        return createOutcome(MISTAKES.toString(), SINGLE, String.valueOf(mistakesNum));
    }

    public Outcome createChecksOutcome(int mistakesNum) {
        return createOutcome(CHECKS, SINGLE, String.valueOf(mistakesNum));
    }

    public Outcome createResetOutcome(int mistakesNum) {
        return createOutcome(RESET, SINGLE, String.valueOf(mistakesNum));
    }

    public Outcome createShowAnswersOutcome(int mistakesNum) {
        return createOutcome(SHOW_ANSWERS, SINGLE, String.valueOf(mistakesNum));
    }

    public Outcome createErrorsOutcome(int errorsNum) {
        return createOutcome(ERRORS.toString(), SINGLE, String.valueOf(errorsNum));
    }
}
