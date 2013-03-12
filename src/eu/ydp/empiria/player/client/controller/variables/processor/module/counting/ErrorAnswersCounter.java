package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.predicates.WrongAnswerPredicate;

public class ErrorAnswersCounter {

	private final GeneralAnswersCounter generalAnswersCounter;
	private final ErrorsToCountModeAdjuster errorsToCountModeAdjuster;
	
	@Inject
	public ErrorAnswersCounter(GeneralAnswersCounter generalAnswersCounter, ErrorsToCountModeAdjuster errorsToCountModeAdjuster) {
		this.generalAnswersCounter = generalAnswersCounter;
		this.errorsToCountModeAdjuster = errorsToCountModeAdjuster;
	}

	public int countErrorAnswersAdjustedToMode(Response response){
		int amountOfErrorAnswers = countErrorsForNotOrderedAnswersInResponse(response);
		CountMode countMode = response.getCountMode();
		int adjustedValue = errorsToCountModeAdjuster.adjustValueToCountMode(amountOfErrorAnswers, countMode);
		return adjustedValue;
	}
	
	public int countErrorsForNotOrderedAnswersInResponse(Response response){
		CorrectAnswers correctAnswers = response.correctAnswers;
		Predicate<String> wrongAnswerPredicate = new WrongAnswerPredicate(correctAnswers);
		
		int errors = generalAnswersCounter.countAnswersMatchingPredicate(response.values, wrongAnswerPredicate);
		return errors;
	}
}
