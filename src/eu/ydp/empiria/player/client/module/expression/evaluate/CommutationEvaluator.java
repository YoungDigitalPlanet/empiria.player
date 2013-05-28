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
				
		Multiset<Multiset<String>> adaptedUserAnswers = convertSpecialCharacters(userAnswers);
		Multiset<Multiset<String>> adaptedCorrectAnswers = convertSpecialCharacters(correctAnswers);
		
		return adaptedUserAnswers.equals(adaptedCorrectAnswers);
	}

	private Multiset<Multiset<String>> convertSpecialCharacters(Multiset<Multiset<String>> answers) {
		Multiset<Multiset<String>> modifiedAnswers = HashMultiset.create();
		
		for (Multiset<String> multiset : answers) {
			Multiset<String> modifiedSubSet = HashMultiset.create();
			for (String string : multiset) {				
				String modifiedString = expressionAdapter.process(string);
				modifiedSubSet.add(modifiedString);
			}
			modifiedAnswers.add(modifiedSubSet);
		}
		return modifiedAnswers;
	}
}
