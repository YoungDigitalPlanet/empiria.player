package eu.ydp.empiria.player.client.module.expression.evaluate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

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
	public void expressionIsWrong() {
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
	public void expressionIsCorrect() {
		// given
		ExpressionBean bean = mock(ExpressionBean.class);
		stub(expressionEvaluator.evaluate(any(ExpressionBean.class))).toReturn(true);
		
		// when
		evaluator.evaluate(bean);
		
		// then
		verify(expressionEvaluator).evaluate(eq(bean));
		verify(commutationEvaluator).evaluate(eq(bean));
	}

}
