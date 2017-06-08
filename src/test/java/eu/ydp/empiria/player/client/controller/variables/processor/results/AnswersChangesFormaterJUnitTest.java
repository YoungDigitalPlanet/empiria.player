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

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class AnswersChangesFormaterJUnitTest {

    private AnswersChangesFormater answersChangesFormater = new AnswersChangesFormater();

    @Test
    public void shouldFormatAddedAnswer() throws Exception {
        List<String> addedAnswers = Lists.newArrayList("addedAnswer");
        List<String> removedAnswers = Lists.newArrayList();
        LastAnswersChanges answersChanges = new LastAnswersChanges(addedAnswers, removedAnswers);

        List<String> formattedAnswers = answersChangesFormater.formatLastAnswerChanges(answersChanges);

        String expectedFormattedAnswer = AnswersChangesFormater.ADDED_ASWER_PREFIX + "addedAnswer";
        assertThat(formattedAnswers, hasItems(expectedFormattedAnswer));
    }

    @Test
    public void shouldFormatRemovedAnswer() throws Exception {
        List<String> addedAnswers = Lists.newArrayList();
        List<String> removedAnswers = Lists.newArrayList("removedAnswer");
        LastAnswersChanges answersChanges = new LastAnswersChanges(addedAnswers, removedAnswers);

        List<String> formattedAnswers = answersChangesFormater.formatLastAnswerChanges(answersChanges);

        String expectedFormattedAnswer = AnswersChangesFormater.REMOVED_ASWER_PREFIX + "removedAnswer";
        assertThat(formattedAnswers, hasItems(expectedFormattedAnswer));
    }

}
