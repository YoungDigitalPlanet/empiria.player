package eu.ydp.empiria.player.client.module.expression;

import com.google.common.collect.Multiset;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.expression.evaluate.ResponseValuesFetcherFunctions;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionSetsFinder {

	private final ResponseFinder responseFinder;
	private final ResponseValuesFetcherFunctions responseValuesFetcherFunctions;

	@Inject
	public ExpressionSetsFinder(ResponseFinder responseFinder, ResponseValuesFetcherFunctions responseValuesFetcherFunctions) {
		this.responseFinder = responseFinder;
		this.responseValuesFetcherFunctions = responseValuesFetcherFunctions;
	}

	public void updateResponsesSetsInExpression(ExpressionBean expression) {
		Multiset<Multiset<String>> corectResponsesSet = responseFinder
				.getResponseMultiSet(expression, responseValuesFetcherFunctions.getCorrectAnswerFetcher());

		expression.setCorectResponses(corectResponsesSet);
	}
}
