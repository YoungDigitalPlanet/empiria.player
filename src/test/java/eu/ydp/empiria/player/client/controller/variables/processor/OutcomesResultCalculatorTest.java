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

package eu.ydp.empiria.player.client.controller.variables.processor;

import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OutcomesResultCalculatorTest {

    @InjectMocks
    private OutcomesResultCalculator calculator;
    @Mock
    private Logger logger;

    @Test
    public void assessmentCompleted() {
        // when
        int progress = calculator.calculateResult(8, 8);

        // then
        assertThat(progress).isEqualTo(100);
    }

    @Test
    public void assessmentPartiallySolved() {
        // when
        int progress = calculator.calculateResult(8, 2);

        // then
        assertThat(progress).isEqualTo(25);
    }

    @Test
    public void assessmentPartiallySolved_shouldFloor() {
        // when
        int progress = calculator.calculateResult(101, 71);

        // then
        assertThat(progress).isEqualTo(70);
    }

    @Test
    public void assessmentNotFullySolved_shouldFloor() {
        // when
        int progress = calculator.calculateResult(501, 500);

        // then
        assertThat(progress).isEqualTo(99);
    }

    @Test
    public void assessmentNotTouched() {
        // when
        int progress = calculator.calculateResult(8, 0);

        // then
        assertThat(progress).isEqualTo(0);
    }

    @Test
    public void assessmentWithTodoIsZero() {
        // when
        int progress = calculator.calculateResult(0, 0);

        // then
        assertThat(progress).isEqualTo(0);
    }

    @Test
    public void invalidCase_doneHigherThanTodo() {
        // when
        calculator.calculateResult(10, 11);

        // then
        verify(logger).severe(anyString());
    }

    @Test
    public void invalidCase_negativeDone() {
        // when
        calculator.calculateResult(10, -1);

        // then
        verify(logger).severe(anyString());
    }

    @Test
    public void invalidCase_negativeTodo() {
        // when
        calculator.calculateResult(-2, 5);

        // then
        verify(logger).severe(anyString());
    }

    @Test
    public void invalidCase_negativeDoneAndTodo() {
        // when
        calculator.calculateResult(-2, -3);

        // then
        verify(logger).severe(anyString());
    }
}
