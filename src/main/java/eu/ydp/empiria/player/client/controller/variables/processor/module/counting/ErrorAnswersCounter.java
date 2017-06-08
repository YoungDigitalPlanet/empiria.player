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

package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.predicates.WrongAnswerPredicate;

public class ErrorAnswersCounter {

    private final GeneralAnswersCounter generalAnswersCounter;
    private final ErrorsToCountModeAdjuster errorsToCountModeAdjuster;

    @Inject
    public ErrorAnswersCounter(GeneralAnswersCounter generalAnswersCounter, ErrorsToCountModeAdjuster errorsToCountModeAdjuster) {
        this.generalAnswersCounter = generalAnswersCounter;
        this.errorsToCountModeAdjuster = errorsToCountModeAdjuster;
    }

    public int countErrorAnswersAdjustedToMode(Response response) {
        int amountOfErrorAnswers = countErrorsForNotOrderedAnswersInResponse(response);
        CountMode countMode = response.getAppropriateCountMode();
        int adjustedValue = errorsToCountModeAdjuster.adjustValueToCountMode(amountOfErrorAnswers, countMode);
        return adjustedValue;
    }

    private int countErrorsForNotOrderedAnswersInResponse(Response response) {
        CorrectAnswers correctAnswers = response.correctAnswers;
        Predicate<String> wrongAnswerPredicate = new WrongAnswerPredicate(correctAnswers);

        int errors = generalAnswersCounter.countAnswersMatchingPredicate(response.values, wrongAnswerPredicate);
        return errors;
    }
}
