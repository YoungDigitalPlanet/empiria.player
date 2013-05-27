package eu.ydp.empiria.player.client.module.expression.evaluate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

@RunWith(JUnitParamsRunner.class)
public class CombinedEvaluatorJUnitTest {

	private CombinedEvaluator evaluator;
	private ExpressionEvaluator expressionEvaluator;
	private CommutationEvaluator commutationEvaluator;
	
	@Before
	public void setUp(){
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
		//verify(commutationEvaluator, never()).evaluate(eq(bean));
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
