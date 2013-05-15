package eu.ydp.empiria.player.client.module.expression;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult;

public class ExpressionEvaluationControllerJUnitTest extends AbstractTestBase  {

	private ExpressionEvaluationController expressionEvaluationController;
	private ResponsesTestingHelper responsesHelper;

	@Before
	public void setUpTest() throws Exception {
		expressionEvaluationController = injector.getInstance(ExpressionEvaluationController.class);
		responsesHelper = new ResponsesTestingHelper();
	}

	@Test
	public void evaluateInequationExpectsCorrect() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"), responsesHelper.getResponse("c", "2"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3>='c'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateInequationExpectsWrong() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"), responsesHelper.getResponse("c", "2"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3<'c'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}	
	
	@Test
	public void evaluateEquationExpectsCorrect() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", "0"), responsesHelper.getResponse("c", "2"));		
		ExpressionBean expression = buildExpressionBean(responses, "('a'+5*'b'+3):2='c'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}	
	
	@Test
	public void evaluateEquationExpectsWrong() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", "0"), responsesHelper.getResponse("c", "5"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3='c'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}	
	
	@Test
	public void evaluateEquationExpressionExpectsValueNotSet() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", ""), responsesHelper.getResponse("c", "5"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3='c'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.VALUES_NOT_SET.equals(result));
	}	
	
	@Test
	public void evaluateInvalidFormedEquationArithmeticException_expectsWrong() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"), responsesHelper.getResponse("c", "2"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}		
	
	@Test
	public void evaluateInvalidFormedEquationStupidityGivenArithmeticException_expectsWrong() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "whatthefuck"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}		
	
	
	@Test
	public void evaluateExpressionWithComa_expectsCorrect() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "0,1"), responsesHelper.getResponse("b", "1"), responsesHelper.getResponse("c", "1.1"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'+'b'='c'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}		
	
	private ExpressionBean buildExpressionBean(List<Response> inputResponses, String template) {
		ExpressionBean expressionBean = new ExpressionBean();
		List<Response> responses = expressionBean.getResponses();
		responses.addAll(inputResponses);
		expressionBean.setTemplate(template);
		return expressionBean;
	}
}
