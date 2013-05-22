package eu.ydp.empiria.player.client.module.expression;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;

public class ResponsesTestingHelper {

	public Response getResponse(String identifier, String value) {
		return new Response(null, Lists.newArrayList(value), null, identifier,  null, null);
	}
	
	public Response getResponse(String identifier, String userAnswer, String correctAnswer) {
		CorrectAnswers answer = new CorrectAnswers();
		answer.add(new ResponseValue(correctAnswer));
		return new Response(answer, Lists.newArrayList(userAnswer), null, identifier,  null, null);
	}
}
