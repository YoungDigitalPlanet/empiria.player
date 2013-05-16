package eu.ydp.empiria.player.client.module.expression;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
	public void evaluateInvalidFormedEquationStupidityArithmeticExceptionCatched_expectsWrong() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "whatthefuck"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}		
	
	@Test
	public void evaluateInvalidFormedEquationAnotherStupidityExceptionCatched_expectsWrong() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "="), responsesHelper.getResponse("b", "="), responsesHelper.getResponse("c", "="), responsesHelper.getResponse("d", "="));
		ExpressionBean expression = buildExpressionBean(responses, "'a''b''c'='d'");
		
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
	
	@Test
	public void evaluateExpressionWithInequationInUserResponse_expectsCorrect() {		
		// given
		List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", "1"), responsesHelper.getResponse("c", "<="), responsesHelper.getResponse("d", "2"));		
		ExpressionBean expression = buildExpressionBean(responses, "'a'+'b''c''d'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}		
	
	@Test
	public void evaluateCommutated_correct() {
		// given
		List<Response> leftResponses = Lists.newArrayList(
				responsesHelper.getResponse("a", "1", "2"), 
				responsesHelper.getResponse("b", "2", "1"));
		List<Response> rightResponses = Lists.newArrayList(
				responsesHelper.getResponse("c", "6", "6"), 
				responsesHelper.getResponse("d", "3", "3"));		
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}
	
	@Test
	public void evaluateCommutated_wrongValues() {
		// given
		List<Response> leftResponses = Lists.newArrayList(
				responsesHelper.getResponse("a", "1", "2"), 
				responsesHelper.getResponse("b", "2", "1"));
		List<Response> rightResponses = Lists.newArrayList(
				responsesHelper.getResponse("c", "6", "7"), 
				responsesHelper.getResponse("d", "3", "4"));		
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}
	
	@Test
	public void evaluateCommutated_wrongExpression() {
		// given
		List<Response> leftResponses = Lists.newArrayList(
				responsesHelper.getResponse("a", "1", "1"), 
				responsesHelper.getResponse("b", "2", "2"));
		List<Response> rightResponses = Lists.newArrayList(
				responsesHelper.getResponse("c", "3", "6"), 
				responsesHelper.getResponse("d", "6", "3"));		
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}
	
	
	@Test
	public void evaluateCommutated_wrongCommutationBetweenSidesOfEquation() {
		// given
		List<Response> leftResponses = Lists.newArrayList(
				responsesHelper.getResponse("a", "1", "1"), 
				responsesHelper.getResponse("b", "2", "4"));	
		List<Response> rightResponses = Lists.newArrayList( 
				responsesHelper.getResponse("c", "4", "2"), 
				responsesHelper.getResponse("d", "3", "3"));		
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'+'d'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}
	
	private ExpressionBean buildExpressionBean(List<Response> inputResponses, String template) {
		ExpressionBean expressionBean = new ExpressionBean();
		List<Response> responses = expressionBean.getResponses();
		responses.addAll(inputResponses);
		expressionBean.setTemplate(template);
		return expressionBean;
	}	
	
	@SuppressWarnings("unchecked")
	private ExpressionBean buildCommutatedExpressionBean(List<Response> leftResponses, List<Response> rightResponses, String template) {
		List<Response> responses = Lists.newArrayList( Iterables.concat(leftResponses, rightResponses) );
		ExpressionBean expressionBean = buildExpressionBean(responses, template);
		expressionBean.setMode(ExpressionMode.COMMUTATION);
		
		List<Set<Response>> setsOfResponses = Lists.<Set<Response>>newArrayList(
				Sets.newHashSet(leftResponses), 
				Sets.newHashSet(rightResponses), 
				Sets.newHashSet(responses));
		expressionBean.setSetsOfResponses(setsOfResponses );
		
		return expressionBean;
	}
}
