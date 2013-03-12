package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;

public class DoneToCountModeAdjuster {

	public int adjustValueToCountMode(int amountOfGivenCorrectAnswers, CorrectAnswers correctAnswers, CountMode countMode){
		int adjustedValue;
		if(countMode == CountMode.CORRECT_ANSWERS){
			adjustedValue = amountOfGivenCorrectAnswers;
		}else{
			adjustedValue = adjustDoneValueToSingleCountMode(amountOfGivenCorrectAnswers, correctAnswers);
		}
		
		return adjustedValue;
	}

	private int adjustDoneValueToSingleCountMode(int amountOfGivenCorrectAnswers, CorrectAnswers correctAnswers) {
		int adjustedValue;
		if(areAllGivenAnswersCorrect(amountOfGivenCorrectAnswers, correctAnswers)){
			adjustedValue = 1;
		}else{
			adjustedValue = 0;
		}
		return adjustedValue;
	}

	private boolean areAllGivenAnswersCorrect(int amountOfGivenCorrectAnswers, CorrectAnswers correctAnswers) {
		int amountOfPossibleCorrectAnswers = correctAnswers.getResponseValuesCount();
		return amountOfGivenCorrectAnswers >= amountOfPossibleCorrectAnswers;
	}
	
}
