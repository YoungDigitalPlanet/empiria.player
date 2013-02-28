package eu.ydp.empiria.player.client.controller.variables.processor.counting;

import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.counting.predicates.WrongAnswerPredicate;

public class ErrorAnswersCounter {

	private GeneralAnswersCounter generalAnswersCounter;
	
	@Inject
	public ErrorAnswersCounter(GeneralAnswersCounter generalAnswersCounter) {
		this.generalAnswersCounter = generalAnswersCounter;
	}

	public int countErrorsForNotOrderedAnswersInResponse(Response response){
		CorrectAnswers correctAnswers = response.correctAnswers;
		Predicate<String> wrongAnswerPredicate = new WrongAnswerPredicate(correctAnswers);
		
		CountMode countMode = response.getCountMode();
		int errors = generalAnswersCounter.countAnswersMatchingPredicateAdjustedToMode(response.values, countMode, wrongAnswerPredicate);
		return errors;
	}
}
