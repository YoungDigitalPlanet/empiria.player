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

package eu.ydp.empiria.player.client.controller.variables.processor.module.ordering;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class OrderingModeVariableProcessorJUnitTest {

    private OrderingModeVariableProcessor orderingModeVariableProcessor;

    @Before
    public void before() {
        orderingModeVariableProcessor = new OrderingModeVariableProcessor();
    }

    @Test
    public void calculateErrorsTest() {
        assertEquals(0, orderingModeVariableProcessor.calculateErrors(null));
    }

    @Test
    public void calculateMistakesTest() {
        assertEquals(0, orderingModeVariableProcessor.calculateMistakes(LastMistaken.CORRECT, 0));
    }

    @Test
    public void checkLastmistakenTest_correctSolution() {
        Response response = new ResponseBuilder().withCurrentUserAnswers(new String[]{"a", "b", "b", "c", "a"})
                .withCorrectAnswers(new String[]{"a", "b", "b", "c", "a"}).build();
        LastAnswersChanges lastAnswerChanges = new LastAnswersChanges(Lists.newArrayList("newAnswer"), Lists.newArrayList("a"));

        LastMistaken result = orderingModeVariableProcessor.checkLastmistaken(response, lastAnswerChanges);
        assertThat(result).isEqualTo(LastMistaken.CORRECT);
    }

    @Test
    public void checkLastmistakenTest_wrongAnswers() {
        Response response = new ResponseBuilder().withCurrentUserAnswers(new String[]{"c", "b", "b", "a", "a"})
                .withCorrectAnswers(new String[]{"a", "b", "b", "c", "a"}).build();
        LastAnswersChanges lastAnswerChanges = new LastAnswersChanges(Lists.newArrayList("newAnswer"), Lists.newArrayList("a"));

        LastMistaken result = orderingModeVariableProcessor.checkLastmistaken(response, lastAnswerChanges);
        assertThat(result).isEqualTo(LastMistaken.NONE);
    }

    @Test
    public void checkLastmistakenTest_wrongCurrentAnswersButWasNoChange() {
        Response response = new ResponseBuilder().withCurrentUserAnswers(new String[]{"c", "b", "b", "a", "a"})
                .withCorrectAnswers(new String[]{"a", "b", "b", "c", "a"}).build();
        LastAnswersChanges lastAnswerChanges = new LastAnswersChanges(new ArrayList<String>(), new ArrayList<String>());

        LastMistaken result = orderingModeVariableProcessor.checkLastmistaken(response, lastAnswerChanges);
        assertThat(result).isEqualTo(LastMistaken.NONE);
    }

    @Test
    public void calculateDoneTest_correctSolution() {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.withCurrentUserAnswers(new String[]{"a", "b", "b", "c", "a"});
        responseBuilder.withCorrectAnswers(new String[]{"a", "b", "b", "c", "a"});

        int calculateDone = orderingModeVariableProcessor.calculateDone(responseBuilder.build());

        assertEquals(1, calculateDone);
    }

    @Test
    public void calculateDoneTest_wrongSolution() {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.withCurrentUserAnswers(new String[]{"a", "b", "c", "b", "a"});
        responseBuilder.withCorrectAnswers(new String[]{"a", "b", "b", "c", "a"});

        int calculateDone = orderingModeVariableProcessor.calculateDone(responseBuilder.build());

        assertEquals(0, calculateDone);
    }

    @Test
    public void evaluateAnswersTest() {

        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.withCurrentUserAnswers(new String[]{"a", "b", "c", "b", "a"});
        responseBuilder.withCorrectAnswers(new String[]{"a", "b", "b", "c", "a"});

        List<Boolean> result = orderingModeVariableProcessor.evaluateAnswers(responseBuilder.build());

        assertEquals(5, result.size());
        assertEquals(Boolean.TRUE, result.get(0));
        assertEquals(Boolean.TRUE, result.get(1));
        assertEquals(Boolean.FALSE, result.get(2));
        assertEquals(Boolean.FALSE, result.get(3));
        assertEquals(Boolean.TRUE, result.get(4));
    }
}
