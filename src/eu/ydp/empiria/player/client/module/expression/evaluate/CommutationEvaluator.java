package eu.ydp.empiria.player.client.module.expression.evaluate;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.expression.ResponseFinder;
import eu.ydp.empiria.player.client.module.expression.adapters.DefaultExpressionCharactersAdapter;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class CommutationEvaluator implements Evaluator {

	private final ResponseValuesFetcherFunctions fetcherFunctions;
	private final ResponseFinder responseFinder;
	
	@Inject
	private DefaultExpressionCharactersAdapter expressionAdapter;

	@Inject
	public CommutationEvaluator(ResponseValuesFetcherFunctions fetcherFunctions, ResponseFinder responseFinder) {
		this.fetcherFunctions = fetcherFunctions;
		this.responseFinder = responseFinder;
	}

	@Override
	public boolean evaluate(ExpressionBean bean) {
			
		Multiset<Multiset<String>> correctAnswers = bean.getCorectResponses();
		Multiset<Multiset<String>> userAnswers = responseFinder.getResponseMultiSet(bean, fetcherFunctions.getUserAnswerFetcher());
				
		HashMultiset<Multiset<String>> adaptedUserAnswers = convertSpecialCharacters(userAnswers);
		HashMultiset<Multiset<String>> adaptedCorrectAnswers = convertSpecialCharacters(correctAnswers);
		
		return adaptedUserAnswers.equals(adaptedCorrectAnswers);
	}

	private HashMultiset<Multiset<String>> convertSpecialCharacters(Multiset<Multiset<String>> answers) {
		HashMultiset<Multiset<String>> modifiedAnswers = HashMultiset.create();
		HashMultiset<String> modifiedSubSet;
		
		for (Multiset<String> multiset : answers) {
			modifiedSubSet = HashMultiset.create();
			for (String string : multiset) {				
				String modifiedString = expressionAdapter.process(string);
				modifiedSubSet.add(modifiedString);
			}
			modifiedAnswers.add(modifiedSubSet);
		}
		return modifiedAnswers;
	}
}
