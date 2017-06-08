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

package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.CorrectAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.ErrorAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;

import java.util.List;

public class MultipleModeVariableProcessor implements VariableProcessor {

    private final ErrorAnswersCounter errorAnswersCounter;
    private final CorrectAnswersCounter correctAnswersCounter;
    private final LastGivenAnswersChecker lastGivenAnswersChecker;
    private final MultipleModeAnswersEvaluator multipleModeAnswersEvaluator;

    @Inject
    public MultipleModeVariableProcessor(ErrorAnswersCounter errorAnswersCounter, CorrectAnswersCounter correctAnswersCounter,
                                         LastGivenAnswersChecker lastGivenAnswersChecker, MultipleModeAnswersEvaluator multipleModeAnswersEvaluator) {
        this.errorAnswersCounter = errorAnswersCounter;
        this.correctAnswersCounter = correctAnswersCounter;
        this.lastGivenAnswersChecker = lastGivenAnswersChecker;
        this.multipleModeAnswersEvaluator = multipleModeAnswersEvaluator;
    }

    @Override
    public int calculateErrors(Response response) {
        int errors = errorAnswersCounter.countErrorAnswersAdjustedToMode(response);
        return errors;
    }

    @Override
    public int calculateDone(Response response) {
        int correctAnswers = correctAnswersCounter.countCorrectAnswersAdjustedToCountMode(response);
        return correctAnswers;
    }

    @Override
    public LastMistaken checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
        boolean areAddedAnswers = answersChanges.getAddedAnswers().size() > 0;
        LastMistaken lastmistaken = LastMistaken.NONE;
        if (areAddedAnswers) {
            boolean isAnyAsnwerIncorrect = lastGivenAnswersChecker.isAnyAsnwerIncorrect(answersChanges.getAddedAnswers(), response.correctAnswers);
            if (isAnyAsnwerIncorrect) {
                lastmistaken = LastMistaken.WRONG;
            } else {
                lastmistaken = LastMistaken.CORRECT;
            }
        }
        return lastmistaken;
    }

    @Override
    public int calculateMistakes(LastMistaken lastmistaken, int previousMistakes) {
        if (lastmistaken == LastMistaken.WRONG) {
            return previousMistakes + 1;
        } else {
            return previousMistakes;
        }
    }

    @Override
    public List<Boolean> evaluateAnswers(Response response) {
        List<Boolean> answersEvaluation = multipleModeAnswersEvaluator.evaluateAnswers(response);
        return answersEvaluation;
    }
}
