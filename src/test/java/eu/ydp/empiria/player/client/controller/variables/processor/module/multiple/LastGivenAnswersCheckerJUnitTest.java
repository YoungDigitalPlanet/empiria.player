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

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LastGivenAnswersCheckerJUnitTest {

    private LastGivenAnswersChecker lastGivenAnswersChecker = new LastGivenAnswersChecker();

    @Test
    public void shouldRecognizeThatAnswerIsIncorrect() throws Exception {
        List<String> answers = Lists.newArrayList("correct", "incorrect");
        CorrectAnswers correctAnswers = createCorrectAnswers("correct");

        boolean anyAnswerIncorrect = lastGivenAnswersChecker.isAnyAsnwerIncorrect(answers, correctAnswers);

        assertThat(anyAnswerIncorrect, equalTo(true));
    }

    @Test
    public void shouldRecognizeThatAllAnswersAreWrong() throws Exception {
        List<String> answers = Lists.newArrayList("correct1", "correct2");
        CorrectAnswers correctAnswers = createCorrectAnswers("correct1", "correct2");

        boolean anyAnswerIncorrect = lastGivenAnswersChecker.isAnyAsnwerIncorrect(answers, correctAnswers);

        assertThat(anyAnswerIncorrect, equalTo(false));
    }

    private CorrectAnswers createCorrectAnswers(String... answers) {
        CorrectAnswers correctAnswers = new CorrectAnswers();
        for (String correctAnswer : answers) {
            ResponseValue responseValue = new ResponseValue(correctAnswer);
            correctAnswers.add(responseValue);
        }
        return correctAnswers;
    }

}
