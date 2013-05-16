package eu.ydp.empiria.player.client.module.expression.evaluate;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class ResponseValuesFetcherFunctions {
	
	private final Function<Response, String> userAnswerFetcher = new Function<Response, String>() {

		@Override
		@Nullable
		public String apply(@Nullable Response response) {
			return response.values.get(0);
		}
	};
	
	private final Function<Response, String> correctAnswerFetcher = new Function<Response, String>() {

		@Override
		@Nullable
		public String apply(@Nullable Response response) {
			return  response.correctAnswers.getSingleAnswer();
		}
	};
	
	public Function<Response, String> getUserAnswerFetcher(){
		return userAnswerFetcher;
	}
	
	public Function<Response, String> getCorrectAnswerFetcher(){
		return correctAnswerFetcher;
	}
}
