package eu.ydp.empiria.player.client.module.expression.evaluate;

import com.google.common.collect.Multiset;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.expression.ResponseFinder;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class CommutationEvaluator implements Evaluator {

	private final ResponseValuesFetcherFunctions fetcherFunctions;
	private final ResponseFinder responseFinder;

	@Inject
	public CommutationEvaluator(ResponseValuesFetcherFunctions fetcherFunctions, ResponseFinder responseFinder) {
		this.fetcherFunctions = fetcherFunctions;
		this.responseFinder = responseFinder;
	}

	@Override
	public boolean evaluate(ExpressionBean bean) {

		Multiset<Multiset<String>> correctAnswers = bean.getCorectResponses();
		Multiset<Multiset<String>> userAnswers = responseFinder.getResponseMultiSet(bean, fetcherFunctions.getUserAnswerFetcher());

		return userAnswers.equals(correctAnswers);
	}
}
