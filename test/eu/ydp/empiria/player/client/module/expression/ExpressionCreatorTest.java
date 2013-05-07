package eu.ydp.empiria.player.client.module.expression;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionCreatorTest {

	private ExpressionCreator expressionCreator;

	@Before
	public void before() {
		expressionCreator = new ExpressionCreator();
	}

	@Test
	public void getExpressionTest() {
		ExpressionBean expressionBean = new ExpressionBean();
		List<Response> responses = Lists.newArrayList(getResponse("a", "0"), getResponse("b", "1"), getResponse("c", "2"), getResponse("d", "3"));
		expressionBean.getResponses().addAll(responses);
		expressionBean.setTemplate("'a'+5*'b'+3>='c'");

		String result = expressionCreator.getExpression(expressionBean);

		assertEquals("0+5*1+3>=2", result);
	}

	private Response getResponse(String identifier, String value) {
		return new Response(null, Lists.newArrayList(value), null, identifier, null, null, null);
	}
}
