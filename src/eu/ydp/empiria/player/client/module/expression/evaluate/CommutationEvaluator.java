package eu.ydp.empiria.player.client.module.expression.evaluate;

import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class CommutationEvaluator implements Evaluator {
	
	@Inject
	private ResponseValuesFetcherFunctions fetcherFunctions;
	
	@Override
	public boolean evaluate(ExpressionBean bean) {
		
		Multiset<Multiset<String>> correctAnswers = fetchAllAnswers(bean.getSetsOfResponses(), fetcherFunctions.getCorrectAnswerFetcher());
		Multiset<Multiset<String>> userAnswers = fetchAllAnswers(bean.getSetsOfResponses(), fetcherFunctions.getUserAnswerFetcher());
		return userAnswers.equals(correctAnswers);
	}

	private Multiset<Multiset<String>> fetchAllAnswers(List<Set<Response>> setsOfResponses, Function<Response, String> userAnswerFetcher) {
		Multiset<Multiset<String>> multisets = HashMultiset.create();
		for (Set<Response> currentSet : setsOfResponses){
			multisets.add(fetchAnswers(currentSet, userAnswerFetcher));
		}
		return multisets;
	}

	private Multiset<String> fetchAnswers(Set<Response> currentSet, Function<Response, String> userAnswerFetcher) {
		Multiset<String> multiset = HashMultiset.create();
		for (Response response : currentSet){
			multiset.add( userAnswerFetcher.apply(response) );
		}
		return multiset;
	}

}
