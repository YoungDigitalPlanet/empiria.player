package eu.ydp.empiria.player.client.module.expression.evaluate;

import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class CommutationEvaluator implements Evaluator {

	@Override
	public boolean evaluate(ExpressionBean bean) {
		Multiset<Multiset<String>> correctAnswers = fetchCorrectAnswers(bean.getSetsOfResponses());
		Multiset<Multiset<String>> userAnswers = fetchUserAnswers(bean.getSetsOfResponses());
		return userAnswers.equals(correctAnswers);
	}

	private Multiset<Multiset<String>> fetchUserAnswers(List<Set<Response>> setsOfResponses) {
		Multiset<Multiset<String>> multisets = HashMultiset.create();
		for (Set<Response> currentSet : setsOfResponses){
			multisets.add(findUserAnswers(currentSet));
		}
		return multisets;
	}

	private Multiset<String> findUserAnswers(Set<Response> currentSet) {
		Multiset<String> multiset = HashMultiset.create();
		for (Response response : currentSet){
			multiset.add( response.values.get(0) );
		}
		return multiset;
	}

	private Multiset<Multiset<String>> fetchCorrectAnswers(List<Set<Response>> setsOfResponses) {
		Multiset<Multiset<String>> multisets = HashMultiset.create();
		for (Set<Response> currentSet : setsOfResponses){
			multisets.add(findCorrectAnswers(currentSet));
		}
		return multisets;
	}

	private Multiset<String> findCorrectAnswers(Set<Response> currentSet) {
		Multiset<String> multiset = HashMultiset.create();
		for (Response response : currentSet){
			multiset.add( response.correctAnswers.getSingleAnswer() );
		}
		return multiset;
	}

}
