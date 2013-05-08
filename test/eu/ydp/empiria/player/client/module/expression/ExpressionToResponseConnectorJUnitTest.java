package eu.ydp.empiria.player.client.module.expression;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.gwt.dev.util.collect.HashMap;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionToResponseConnectorJUnitTest {

	private ExpressionToResponseConnector expressionToResponseConnector;

	@Mock
	private IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor;

	@Before
	public void setUp() throws Exception {
		expressionToResponseConnector = new ExpressionToResponseConnector(identifiersFromExpressionExtractor);
	}

	@Test
	public void shouldConnectOnlyResponsesRelatedToExpression() throws Exception {
		// given
		String template = "template";
		ExpressionBean expressionBean = new ExpressionBean();
		expressionBean.setTemplate(template);

		Response relatedResponse = Mockito.mock(Response.class);
		Response unrelatedResponse = Mockito.mock(Response.class);
		Map<String, Response> responses = new HashMap<String, Response>();
		responses.put("relatedResponse", relatedResponse);
		responses.put("unrelatedResponse", unrelatedResponse);

		when(identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(template))
			.thenReturn(Lists.newArrayList("relatedResponse"));

		// when
		expressionToResponseConnector.connectResponsesToExpression(expressionBean, responses);

		// then
		verify(identifiersFromExpressionExtractor).extractResponseIdentifiersFromTemplate(template);
		verify(relatedResponse).setExpression(expressionBean);
		List<Response> connectedResponses = expressionBean.getResponses();
		assertEquals(1, connectedResponses.size());
		assertEquals(relatedResponse, connectedResponses.get(0));
	}

	@Test
	public void shouldIgnoreIdentifierWhenNoResponseExistsForIt() throws Exception {
		// given
		String template = "template";
		ExpressionBean expressionBean = new ExpressionBean();
		expressionBean.setTemplate(template);

		Response unrelatedResponse = Mockito.mock(Response.class);
		Map<String, Response> responses = new HashMap<String, Response>();
		responses.put("unrelatedResponse", unrelatedResponse);

		when(identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(template))
			.thenReturn(Lists.newArrayList("relatedResponse"));

		// when
		expressionToResponseConnector.connectResponsesToExpression(expressionBean, responses);

		// then
		verify(identifiersFromExpressionExtractor).extractResponseIdentifiersFromTemplate(template);
		List<Response> connectedResponses = expressionBean.getResponses();
		assertEquals(0, connectedResponses.size());
	}
}
