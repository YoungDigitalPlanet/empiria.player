package eu.ydp.empiria.player.client.module.expression;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.exception.CannotDivideExpressionToPartsException;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

public class ExpressionToPartsDivider {

	public static final String EQUAL_SIGN = "=";
	private static final Logger LOGGER = new Logger();

	public List<String> divideExpressionOnEquality(String template, List<Response> responses, Function<Response, String> answerFetcher) {
		String templateWithEquality = template;
		if (!template.contains(EQUAL_SIGN)) {
			templateWithEquality = replaceResponseIdWithEqualitySign(template, responses, answerFetcher);
		}

		String[] leftRightExpressionParts = templateWithEquality.split(EQUAL_SIGN);
		if (leftRightExpressionParts.length == 2) {
			return Arrays.asList(leftRightExpressionParts);
		} else {
			LOGGER.severe("Expression divided on EQUAL sign contains different amount of parts than 2!");
			return Arrays.asList(leftRightExpressionParts);
		}
	}

	private String replaceResponseIdWithEqualitySign(String template, List<Response> responses, Function<Response, String> answerFetcher) {
		Response response = findResponseWithAnswerAsEqualSign(responses, answerFetcher);

		String responseId = response.getID();
		String updatedTemplate = template.replace("'" + responseId + "'", EQUAL_SIGN);

		return updatedTemplate;
	}

	private Response findResponseWithAnswerAsEqualSign(List<Response> responses, Function<Response, String> answerFetcher) {
		for (Response response : responses) {
			if (answerFetcher.apply(response).equals(EQUAL_SIGN)) {
				return response;
			}
		}

		throw new CannotDivideExpressionToPartsException("Cannot divide expression on equal sign, template or any response dont contain equal sign!");
	}

}
