package eu.ydp.empiria.player.client.module.expression;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.expression.evaluate.ResponseValuesFetcherFunctions;

public class ResponseFinderJUnitTest {

	private ResponseFinder identifierToValueConverter;
	private ResponseValuesFetcherFunctions responseValuesFetcherFunctions;

	@Before
	public void before() {
		identifierToValueConverter = new ResponseFinder(new ExpressionToPartsDivider(), new IdentifiersFromExpressionExtractor());
		responseValuesFetcherFunctions = new ResponseValuesFetcherFunctions();
	}

	@Test
	public void getCorectValuesTest() {
		List<String> responseIdentifiers = Lists.newArrayList("a", "b", "c");
		List<Response> responses = Lists.newArrayList(getResponses("c", "1"), getResponses("b", "34"), getResponses("a", "3"));

		List<String> corectValues = identifierToValueConverter.getCorectValues(responseIdentifiers, responses,
				responseValuesFetcherFunctions.getCorrectAnswerFetcher());

		assertTrue(corectValues.contains("1"));
		assertTrue(corectValues.contains("34"));
		assertTrue(corectValues.contains("3"));
	}

	private Response getResponses(String idResponse, String correctValue) {
		ResponseBuilder responseBuilder = new ResponseBuilder();
		responseBuilder.withCorrectAnswers(correctValue);
		responseBuilder.withIdentifier(idResponse);
		return responseBuilder.build();
	}
}
