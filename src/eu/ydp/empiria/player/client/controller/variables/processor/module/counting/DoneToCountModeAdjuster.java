package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class DoneToCountModeAdjuster {

	public int adjustValueToCountMode(int amountOfGivenCorrectAnswers, Response response, CountMode countMode){
		int adjustedValue;
		if(countMode == CountMode.CORRECT_ANSWERS){
			adjustedValue = amountOfGivenCorrectAnswers;
		}else{
			adjustedValue = adjustDoneValueToSingleCountMode(amountOfGivenCorrectAnswers, response);
		}
		
		return adjustedValue;
	}

	private int adjustDoneValueToSingleCountMode(int amountOfGivenCorrectAnswers, Response response) {
		int adjustedValue;
		if(isSolvedWithoutErrors(amountOfGivenCorrectAnswers, response)){
			adjustedValue = 1;
		}else{
			adjustedValue = 0;
		}
		return adjustedValue;
	}

	private boolean isSolvedWithoutErrors(int amountOfGivenCorrectAnswers, Response response) {
		boolean isSolvedWithoutErrors = areAllGivenAnswersCorrect(amountOfGivenCorrectAnswers, response) && allRequiredAnswersGiven(amountOfGivenCorrectAnswers, response.correctAnswers);
		return isSolvedWithoutErrors;
	}

	private boolean allRequiredAnswersGiven(int amountOfGivenCorrectAnswers, CorrectAnswers correctAnswers) {
		return amountOfGivenCorrectAnswers == correctAnswers.getResponseValuesCount();
	}

	private boolean areAllGivenAnswersCorrect(int amountOfGivenCorrectAnswers, Response response) {
		int amountOfGivenAnswers = response.values.size();
		return amountOfGivenAnswers == amountOfGivenCorrectAnswers;
	}
	
}
