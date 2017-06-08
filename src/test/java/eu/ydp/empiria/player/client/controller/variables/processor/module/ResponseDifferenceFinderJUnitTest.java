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

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseDifferenceFinderJUnitTest {

    private ResponseDifferenceFinder responseDifferenceFinder = new ResponseDifferenceFinder();

    @Test
    public void shouldRecognizeNewAnswerWasAdded() throws Exception {

        List<String> previousAnswers = Lists.newArrayList();
        List<String> currentAnswers = Lists.newArrayList("newAnswer");

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), hasItem("newAnswer"));
        assertThat(answersChanges.getRemovedAnswers(), is(empty()));
    }

    @Test
    public void shouldRecognizeAnswerWasRemoved() throws Exception {

        List<String> previousAnswers = Lists.newArrayList("answerToRemove");
        List<String> currentAnswers = Lists.newArrayList();

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), is(empty()));
        assertThat(answersChanges.getRemovedAnswers(), hasItem("answerToRemove"));
    }

    @Test
    public void shouldNotFindAnyChangesWhenAnswersAreSame() throws Exception {

        List<String> previousAnswers = Lists.newArrayList("answer");
        List<String> currentAnswers = Lists.newArrayList(previousAnswers);

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), is(empty()));
        assertThat(answersChanges.getRemovedAnswers(), is(empty()));
    }

    @Test
    public void shouldIgnoreEmptyAnswers() throws Exception {

        List<String> previousAnswers = Lists.newArrayList("");
        List<String> currentAnswers = Lists.newArrayList("");

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), is(empty()));
        assertThat(answersChanges.getRemovedAnswers(), is(empty()));
    }

}
