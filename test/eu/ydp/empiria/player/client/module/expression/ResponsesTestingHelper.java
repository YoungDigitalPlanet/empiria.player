package eu.ydp.empiria.player.client.module.expression;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;

public class ResponsesTestingHelper {

	public Response getResponse(String identifier, String value) {
		return new ResponseBuilder().withIdentifier(identifier).withCurrentUserAnswers(value).build();
	}

	public Response getResponse(String identifier, String userAnswer, String correctAnswer) {
		CorrectAnswers answer = new CorrectAnswers();
		answer.add(new ResponseValue(correctAnswer));
		return new ResponseBuilder().withIdentifier(identifier).withCurrentUserAnswers(userAnswer).withCorrectAnswers(correctAnswer).build();
	}
}
