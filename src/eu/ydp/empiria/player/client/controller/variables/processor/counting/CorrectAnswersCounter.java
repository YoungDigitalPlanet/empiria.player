package eu.ydp.empiria.player.client.controller.variables.processor.counting;

import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.counting.predicates.CorrectAnswerPredicate;

public class CorrectAnswersCounter {

	private GeneralAnswersCounter generalAnswersCounter;
	
	@Inject
	public CorrectAnswersCounter(GeneralAnswersCounter generalAnswersCounter) {
		this.generalAnswersCounter = generalAnswersCounter;
	}

	public int countCorrectForNotOrderedAnswers(Response response){
		CorrectAnswers correctAnswers = response.correctAnswers;
		Predicate<String> correctAnswerPredicate = new CorrectAnswerPredicate(correctAnswers);
		
		CountMode countMode = response.getCountMode();
		int amountOfCorrectAnswers = generalAnswersCounter.countAnswersMatchingPredicateAdjustedToMode(response.values, countMode, correctAnswerPredicate);
		return amountOfCorrectAnswers;
	}
}
