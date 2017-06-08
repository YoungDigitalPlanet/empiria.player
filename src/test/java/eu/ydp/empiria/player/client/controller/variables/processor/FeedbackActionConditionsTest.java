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

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class FeedbackActionConditionsTest {

    @InjectMocks
    private FeedbackActionConditions feedbackActionConditions;
    @Mock
    private OutcomeAccessor outcomeAccessor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters(method = "pageAllOkParameters")
    public void shouldReturnPageAllOk(int todo, int done, boolean expectedResult) {
        // given
        mockTodo(todo);
        mockDone(done);

        // when
        boolean pageAllOk = feedbackActionConditions.isPageAllOk();

        // then
        assertThat(pageAllOk).isEqualTo(expectedResult);

        verify(outcomeAccessor).getCurrentPageTodo();
        verify(outcomeAccessor).getCurrentPageDone();
    }

    @SuppressWarnings({"unused"})
    private Object[] pageAllOkParameters() {
        return $($(5, 5, true), $(5, 6, false), $(0, 6, false));
    }

    @Test
    @Parameters(method = "pageAllOkWithoutPreviousErrorsParameters")
    public void shouldReturnPageAllOkWithoutPreviousErrors(int todo, int done, int errors, boolean expectedResult) {
        // given
        mockTodo(todo);
        mockDone(done);
        mockErrors(errors);

        // when
        boolean pageAllOkWithoutPreviousErrors = feedbackActionConditions.isPageAllOkWithoutPreviousErrors();

        // then
        assertThat(pageAllOkWithoutPreviousErrors).isEqualTo(expectedResult);

        verify(outcomeAccessor).getCurrentPageErrors();
    }

    @SuppressWarnings({"unused"})
    private Object[] pageAllOkWithoutPreviousErrorsParameters() {
        return $($(5, 5, 0, true), $(5, 5, 1, false), $(5, 4, 1, false), $(5, 4, 0, false));
    }

    private void mockTodo(Integer TODO) {
        when(outcomeAccessor.getCurrentPageTodo()).thenReturn(TODO);
    }

    private void mockDone(Integer DONE) {
        when(outcomeAccessor.getCurrentPageDone()).thenReturn(DONE);
    }

    private void mockErrors(Integer errorCount) {
        when(outcomeAccessor.getCurrentPageErrors()).thenReturn(errorCount);
    }
}
