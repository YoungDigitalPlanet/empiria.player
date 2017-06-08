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

package eu.ydp.empiria.player.client.module.expression.evaluate;

import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class CombinedEvaluatorJUnitTest {

    private CombinedEvaluator evaluator;
    private ExpressionEvaluator expressionEvaluator;
    private CommutationEvaluator commutationEvaluator;

    @Before
    public void setUp() {
        expressionEvaluator = mock(ExpressionEvaluator.class);
        commutationEvaluator = mock(CommutationEvaluator.class);
        evaluator = new CombinedEvaluator(expressionEvaluator, commutationEvaluator);
    }

    @Test
    public void checkCommutation_expressionIsWrong() {
        // given
        ExpressionBean bean = mock(ExpressionBean.class);
        stub(expressionEvaluator.evaluate(any(ExpressionBean.class))).toReturn(false);

        // when
        evaluator.evaluate(bean);

        // then
        verify(expressionEvaluator).evaluate(eq(bean));
        verify(commutationEvaluator, never()).evaluate(eq(bean));
    }

    @Test
    public void checkCommutation_expressionIsCorrect() {
        // given
        ExpressionBean bean = mock(ExpressionBean.class);
        stub(expressionEvaluator.evaluate(any(ExpressionBean.class))).toReturn(true);

        // when
        evaluator.evaluate(bean);

        // then
        verify(expressionEvaluator).evaluate(eq(bean));
        verify(commutationEvaluator).evaluate(eq(bean));
    }

    @Test
    public void result_correct() {
        // given
        ExpressionBean bean = mock(ExpressionBean.class);
        stub(expressionEvaluator.evaluate(any(ExpressionBean.class))).toReturn(true);
        stub(commutationEvaluator.evaluate(any(ExpressionBean.class))).toReturn(true);

        // when
        boolean result = evaluator.evaluate(bean);

        // then
        assertThat(result, equalTo(true));
    }

    @Test
    @Parameters({"false, true", "true, false", "false, false"})
    public void result_wrong(boolean expressionResult, boolean commutationResult) {
        // given
        ExpressionBean bean = mock(ExpressionBean.class);
        stub(expressionEvaluator.evaluate(any(ExpressionBean.class))).toReturn(expressionResult);
        stub(commutationEvaluator.evaluate(any(ExpressionBean.class))).toReturn(commutationResult);

        // when
        boolean result = evaluator.evaluate(bean);

        // then
        assertThat(result, equalTo(false));
    }

}
