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

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CorrectAnswersCounterJUnitTest {

    private CorrectAnswersCounter correctAnswersCounter;

    private GeneralAnswersCounter generalAnswersCounter;
    private DoneToCountModeAdjuster doneToCountModeAdjuster;

    @Before
    public void setUp() throws Exception {
        generalAnswersCounter = new GeneralAnswersCounter();
        doneToCountModeAdjuster = new DoneToCountModeAdjuster();

        correctAnswersCounter = new CorrectAnswersCounter(generalAnswersCounter, doneToCountModeAdjuster);
    }

    @Test
    public void shouldCountEvenOneErrorAsNoCorrectAnswersAtAllInSingleCountMode() throws Exception {
        Response response = new ResponseBuilder().withCorrectAnswers("correct1", "correct2").withCurrentUserAnswers("correct1", "correct2", "wrong")
                .withCountMode(CountMode.SINGLE).build();

        int amountOfCorrectAnswers = correctAnswersCounter.countCorrectAnswersAdjustedToCountMode(response);

        assertThat(amountOfCorrectAnswers, equalTo(0));
    }

    @Test
    public void shouldCountAllAnswersCorrectAsOneCorrectInSingleCountMode() throws Exception {
        Response response = new ResponseBuilder().withCorrectAnswers("correct1", "correct2").withCurrentUserAnswers("correct1", "correct2")
                .withCountMode(CountMode.SINGLE).build();

        int amountOfCorrectAnswers = correctAnswersCounter.countCorrectAnswersAdjustedToCountMode(response);

        assertThat(amountOfCorrectAnswers, equalTo(1));
    }

    @Test
    public void shouldCountAmountOfCorrectAnswersReducedByErrorsInCorrectAnswersMode() throws Exception {
        Response response = new ResponseBuilder().withCorrectAnswers("correct1", "correct2", "correct3")
                .withCurrentUserAnswers("correct1", "correct2", "correct3", "wrong").withCountMode(CountMode.CORRECT_ANSWERS).build();

        int amountOfCorrectAnswers = correctAnswersCounter.countCorrectAnswersAdjustedToCountMode(response);

        assertThat(amountOfCorrectAnswers, equalTo(2));
    }

}
