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

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ModuleTodoCalculatorJUnitTest {

    private final ModuleTodoCalculator moduleTodoCalculator = new ModuleTodoCalculator();

    @Test
    public void shouldCalculateTodoWhenCountModeSingle() throws Exception {
        Response response = new ResponseBuilder().withCorrectAnswers("first", "second").withCountMode(CountMode.SINGLE).build();

        int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

        assertThat(calculatedTodo, equalTo(1));
    }

    @Test
    public void shouldCalculateTodoWhenCountModeSingleAndThereIsNoCorrectAnswers() throws Exception {
        Response response = new ResponseBuilder().withCorrectAnswers().withCountMode(CountMode.SINGLE).build();

        int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

        assertThat(calculatedTodo, equalTo(0));
    }

    @Test
    public void shouldCalculateTodoWhenCountModeCorrectAnswers() throws Exception {
        Response response = new ResponseBuilder().withCorrectAnswers("first", "second").withCountMode(CountMode.CORRECT_ANSWERS).build();

        int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

        assertThat(calculatedTodo, equalTo(2));
    }

    @Test
    public void shouldCalculateTodoWhenCountModeCorrectAnswersAndThereIsNoCorrectAnswers() throws Exception {
        Response response = new ResponseBuilder().withCorrectAnswers().withCountMode(CountMode.CORRECT_ANSWERS).build();

        int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

        assertThat(calculatedTodo, equalTo(0));
    }
}
