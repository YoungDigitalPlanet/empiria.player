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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class OrderedResponseChangesFinderJUnitTest {

    private OrderedResponseChangesFinder orderedResponseChangesFinder;

    @Before
    public void before() {
        orderedResponseChangesFinder = new OrderedResponseChangesFinder();
    }

    @Test
    public void findChangesOfAnswersTest_noChanges_shouldReturnResultWithEmptyLists() {
        List<String> previousAnswers = Lists.newArrayList("a", "b", "c", "d");
        List<String> currentAnswers = Lists.newArrayList("a", "b", "c", "d");

        LastAnswersChanges result = orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertTrue(result.getAddedAnswers().isEmpty());
        assertTrue(result.getRemovedAnswers().isEmpty());
    }

    @Test
    public void findChangesOfAnswersTest_foundChanges_shouldReturnResultWithLists() {
        List<String> previousAnswers = Lists.newArrayList("a", "b", "c", "d");
        List<String> currentAnswers = Lists.newArrayList("a", "c", "d", "b");

        LastAnswersChanges result = orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertSame(previousAnswers, result.getRemovedAnswers());
        assertSame(currentAnswers, result.getAddedAnswers());
    }
}
