package eu.ydp.empiria.player.client.module.expression;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionValidatorTest {

	private ExpressionValidator expressionValidator;

	@Before
	public void before() {
		expressionValidator = new ExpressionValidator();
	}

	@Test
	public void isAllResponsesAreNotEmptyTest_responsesListIsEmpty() {
		ExpressionBean expressionBean = new ExpressionBean();

		boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

		assertFalse(result);
	}

	@Test
	public void isAllResponsesAreNotEmptyTest_responseHasValueListNull() {
		ExpressionBean expressionBean = new ExpressionBean();

		List<Response> responses = expressionBean.getResponses();
		Response response = getEmptyResponse();
		responses.add(response);

		boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

		assertFalse(result);
	}

	@Test
	public void isAllResponsesAreNotEmptyTest_responseHasValueListEmpty() {
		ExpressionBean expressionBean = new ExpressionBean();

		List<Response> responses = expressionBean.getResponses();
		Response response = getEmptyResponse();
		response.values = new ArrayList<String>();

		responses.add(response);

		boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

		assertFalse(result);
	}

	@Test
	public void isAllResponsesAreNotEmptyTest_responseHasValueListElementEmpty() {
		ExpressionBean expressionBean = new ExpressionBean();

		List<Response> responses = expressionBean.getResponses();
		Response response = getEmptyResponse();
		response.values = new ArrayList<String>();
		response.values.add("ok");
		responses.add(response);

		response = getEmptyResponse();
		response.values = new ArrayList<String>();
		response.values.add("");
		responses.add(response);

		boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

		assertFalse(result);
	}

	@Test
	public void isAllResponsesAreNotEmptyTest_shouldByOk() {
		ExpressionBean expressionBean = new ExpressionBean();

		List<Response> responses = expressionBean.getResponses();
		Response response = getEmptyResponse();
		response.values = new ArrayList<String>();
		response.values.add("ok");

		responses.add(response);

		boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

		assertTrue(result);
	}

	private Response getEmptyResponse() {
		return new Response(null, null, null, null, null, null, null);
	}
}
