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

package eu.ydp.empiria.player.client.module.selection.controller;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.components.choicebutton.Identifiable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class IdentifiableAnswersByTypeFinderJUnitTest {

    private IdentifiableAnswersByTypeFinder answersByTypeFinder;
    private AbstractResponseModel responseModel;

    @Before
    public void setUp() throws Exception {
        answersByTypeFinder = new IdentifiableAnswersByTypeFinder();
        responseModel = mock(AbstractResponseModel.class);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.verifyNoMoreInteractions(responseModel);
    }

    @Test
    public void testFindAnswersObjectsOfGivenType_findCorrectAnswers() {
        TestIdentifiableObject correctObject = createObject("correct");
        TestIdentifiableObject notCorrectObject = createObject("notCorrect");

        List<TestIdentifiableObject> identifiableObjects = Lists.newArrayList(correctObject, notCorrectObject);

        when(responseModel.isCorrectAnswer("correct")).thenReturn(true);

        when(responseModel.isCorrectAnswer("notCorrect")).thenReturn(false);

        // then
        List<TestIdentifiableObject> resultObjects = answersByTypeFinder.findAnswersObjectsOfGivenType(MarkAnswersType.CORRECT, identifiableObjects,
                responseModel);

        assertEquals(1, resultObjects.size());
        assertEquals(correctObject, resultObjects.get(0));
        verify(responseModel).isCorrectAnswer("correct");
        verify(responseModel).isCorrectAnswer("notCorrect");
    }

    @Test
    public void testFindAnswersObjectsOfGivenType_findWrongAnswers() {
        TestIdentifiableObject correctObject = createObject("correct");
        TestIdentifiableObject notCorrectObject = createObject("notCorrect");

        List<TestIdentifiableObject> identifiableObjects = Lists.newArrayList(correctObject, notCorrectObject);

        when(responseModel.isCorrectAnswer("correct")).thenReturn(true);

        when(responseModel.isCorrectAnswer("notCorrect")).thenReturn(false);

        // then
        List<TestIdentifiableObject> resultObjects = answersByTypeFinder.findAnswersObjectsOfGivenType(MarkAnswersType.WRONG, identifiableObjects,
                responseModel);

        assertEquals(1, resultObjects.size());
        assertEquals(notCorrectObject, resultObjects.get(0));
        verify(responseModel).isCorrectAnswer("correct");
        verify(responseModel).isCorrectAnswer("notCorrect");
    }

    private TestIdentifiableObject createObject(String id) {
        return new TestIdentifiableObject(id);
    }

    private class TestIdentifiableObject implements Identifiable {
        private String id;

        TestIdentifiableObject(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }
    }
}
