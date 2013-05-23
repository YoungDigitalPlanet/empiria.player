package eu.ydp.empiria.player.client.module.expression;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.evaluate.ResponseValuesFetcherFunctions;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionEvaluationResult;

public class ExpressionEvaluationControllerJUnitTest extends AbstractTestBase {

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
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"),
				responsesHelper.getResponse("c", "2"));
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3>='c'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateInequationExpectsWrong() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"),
				responsesHelper.getResponse("c", "2"));
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3<'c'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateEquationExpectsCorrect() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", "0"),
				responsesHelper.getResponse("c", "2"));
		ExpressionBean expression = buildExpressionBean(responses, "('a'+5*'b'+3):2='c'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateEquationExpectsWrong() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", "0"),
				responsesHelper.getResponse("c", "5"));
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3='c'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateEquationExpressionExpectsValueNotSet() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", ""),
				responsesHelper.getResponse("c", "5"));
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3='c'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.VALUES_NOT_SET.equals(result));
	}

	@Test
	public void evaluateInvalidFormedEquationArithmeticException_expectsWrong() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"),
				responsesHelper.getResponse("c", "2"));
		ExpressionBean expression = buildExpressionBean(responses, "'a'+5*'b'+3");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateInvalidFormedEquationStupidityArithmeticExceptionCatched_expectsWrong() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "whatthefuck"));
		ExpressionBean expression = buildExpressionBean(responses, "'a'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateInvalidFormedEquationAnotherStupidityExceptionCatched_expectsWrong() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "="), responsesHelper.getResponse("b", "="),
				responsesHelper.getResponse("c", "="), responsesHelper.getResponse("d", "="));
		ExpressionBean expression = buildExpressionBean(responses, "'a''b''c'='d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateExpressionWithComa_expectsCorrect() {
		// given

		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "0,1"), responsesHelper.getResponse("b", "1,1"),
				responsesHelper.getResponse("c", "1.2"));

		ExpressionBean expression = buildExpressionBean(responses, "'a'+'b'='c'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateExpressionWithInequationInUserResponse_expectsCorrect() {
		// given
		List<Response> responses = newArrayList(responsesHelper.getResponse("a", "1"), responsesHelper.getResponse("b", "1"),
				responsesHelper.getResponse("c", "<="), responsesHelper.getResponse("d", "2"));
		ExpressionBean expression = buildExpressionBean(responses, "'a'+'b''c''d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateCommutated_correct() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "1", "2"), responsesHelper.getResponse("b", "2", "1"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "6", "6"), responsesHelper.getResponse("d", "3", "3"));

		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateCommutated_correctFloatingPointDot() {
		// given
		List<Response> leftResponses = newArrayList(
				responsesHelper.getResponse("a", "1.1", "2"), 
				responsesHelper.getResponse("b", "2", "1.1"));
		List<Response> rightResponses = newArrayList(
				responsesHelper.getResponse("c", "6.1", "6.1"), 
				responsesHelper.getResponse("d", "3", "3"));		
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}	
	
	@Test
	public void evaluateCommutated_correctFloatingPointComma() {
		// given
		List<Response> leftResponses = newArrayList(
				responsesHelper.getResponse("a", "1,1", "2"), 
				responsesHelper.getResponse("b", "2", "1,1"));
		List<Response> rightResponses = newArrayList(
				responsesHelper.getResponse("c", "6,1", "6,1"), 
				responsesHelper.getResponse("d", "3", "3"));		
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");
		
		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);
		
		// then
		// TODO
		//assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}	
	
	@Test
	public void evaluateCommutated_correctSidesSwitched() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "2", "4"), responsesHelper.getResponse("b", "3", "1"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "1", "3"), responsesHelper.getResponse("d", "4", "2"));
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'+'d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateCommutated_wrongValues() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "1", "2"), responsesHelper.getResponse("b", "2", "1"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "6", "7"), responsesHelper.getResponse("d", "3", "4"));
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateCommutated_wrongExpression() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "1", "1"), responsesHelper.getResponse("b", "2", "2"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "3", "6"), responsesHelper.getResponse("d", "6", "3"));
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'-'d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateCommutated_wrongCommutationBetweenSidesOfEquation() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "1", "1"), responsesHelper.getResponse("b", "2", "4"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "4", "2"), responsesHelper.getResponse("d", "3", "3"));
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'+'d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateCommutated_wrongCommutationItem() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "1", "11"), responsesHelper.getResponse("b", "14", "4"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "15", "15"));
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a'+'b'='c'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.WRONG.equals(result));
	}

	@Test
	public void evaluateCommutated_allUser_correctExpressionToNumber() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "-", "2"), responsesHelper.getResponse("sign1", "0", "-"),
				responsesHelper.getResponse("b", "2", "0"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "3", "5"), responsesHelper.getResponse("sign3", "-", "-"),
				responsesHelper.getResponse("d", "5", "3"));
		ExpressionBean expression = buildCommutatedExpressionBean(leftResponses, rightResponses, "'a''sign1''b'='c''sign3''d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateCommutated_withSign_correct() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "1", "1"), responsesHelper.getResponse("b", "4", "4"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "3", "2"), responsesHelper.getResponse("d", "2", "3"));
		List<Response> signResponse = newArrayList(responsesHelper.getResponse("sign", "=", "="));
		ExpressionBean expression = buildCommutatedExpressionBeanWithSign(leftResponses, rightResponses, signResponse, "'a'+'b''sign''c'+'d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateCommutated_withSign_correctSidesSwitched() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "1", "3"), responsesHelper.getResponse("b", "4", "2"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "3", "1"), responsesHelper.getResponse("d", "2", "4"));
		List<Response> signResponse = newArrayList(responsesHelper.getResponse("sign", "=", "="));
		ExpressionBean expression = buildCommutatedExpressionBeanWithSign(leftResponses, rightResponses, signResponse, "'a'+'b''sign''c'+'d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateCommutated_withSign_allUser() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "4", "1"), responsesHelper.getResponse("sign1", "+", "+"),
				responsesHelper.getResponse("b", "1", "4"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "3", "2"), responsesHelper.getResponse("sign3", "+", "+"),
				responsesHelper.getResponse("d", "2", "3"));
		List<Response> signResponses = newArrayList(responsesHelper.getResponse("sign2", "=", "="));
		ExpressionBean expression = buildCommutatedExpressionBeanWithSign(leftResponses, rightResponses, signResponses, "'a''sign1''b''sign2''c''sign3''d'");

		// when
		ExpressionEvaluationResult result = expressionEvaluationController.evaluateExpression(expression);

		// then
		assertTrue(ExpressionEvaluationResult.CORRECT.equals(result));
	}

	@Test
	public void evaluateCommutated_withSign_allUser_wrongSignPlace() {
		// given
		List<Response> leftResponses = newArrayList(responsesHelper.getResponse("a", "4", "1"), responsesHelper.getResponse("sign1", "+", "+"),
				responsesHelper.getResponse("b", "1", "4"));
		List<Response> rightResponses = newArrayList(responsesHelper.getResponse("c", "0", "0"), responsesHelper.getResponse("sign3", "=", "+"),
				responsesHelper.getResponse("d", "5", "5"));
		List<Response> signResponses = newArrayList(responsesHelper.getResponse("sign2", "+", "="));
		ExpressionBean expression = buildCommutatedExpressionBeanWithSign(leftResponses, rightResponses, signResponses, "'a''sign1''b''sign2''c''sign3''d'");

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

	private ExpressionBean buildCommutatedExpressionBean(List<Response> leftResponses, List<Response> rightResponses, String template) {
		List<Response> responses = newArrayList(Iterables.concat(leftResponses, rightResponses));
		ExpressionBean expressionBean = buildCommutatedExpressionBean(leftResponses, rightResponses, responses, template);

		return expressionBean;
	}

	private ExpressionBean buildCommutatedExpressionBeanWithSign(List<Response> leftResponses, List<Response> rightResponses, List<Response> otherResponses,
			String template) {
		List<Response> responses = newArrayList(Iterables.concat(leftResponses, rightResponses, otherResponses));
		ExpressionBean expressionBean = buildCommutatedExpressionBean(leftResponses, rightResponses, responses, template);

		return expressionBean;
	}

	private ExpressionBean buildCommutatedExpressionBean(List<Response> leftResponses, List<Response> rightResponses, List<Response> allResponses,
			String template) {
		ExpressionBean expressionBean = buildExpressionBean(allResponses, template);
		expressionBean.setMode(ExpressionMode.COMMUTATION);
		ResponseFinder responseFinder = new ResponseFinder(new ExpressionToPartsDivider(), new IdentifiersFromExpressionExtractor());
		Multiset<Multiset<String>> correctAnswerMultiSet = createSetWithCorrectAnswer(responseFinder, expressionBean);

		expressionBean.setCorectResponses(correctAnswerMultiSet);

		return expressionBean;
	}

	private Multiset<Multiset<String>> createSetWithCorrectAnswer(ResponseFinder responseFinder, ExpressionBean expression) {
		return responseFinder.getResponseMultiSet(expression, new ResponseValuesFetcherFunctions().getCorrectAnswerFetcher());
	}
}
