package eu.ydp.empiria.player.client.module.expression;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionCreatorJUnitTest {

	private ExpressionCreator expressionCreator;
	private ResponsesTestingHelper responsesHelper;

	@Before
	public void before() {
		expressionCreator = new ExpressionCreator();
		responsesHelper = new ResponsesTestingHelper();
	}

	@Test
	public void getExpressionTest() {
		ExpressionBean expressionBean = new ExpressionBean();
		List<Response> responses = Lists.newArrayList(
				responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"), 
				responsesHelper.getResponse("c", "2"), responsesHelper.getResponse("d", "3"));
		expressionBean.getResponses().addAll(responses);
		expressionBean.setTemplate("'a'+5*'b'+3>='c'");

		String result = expressionCreator.getExpression(expressionBean);

		assertEquals("0+5*1+3>=2", result);
	}

}
