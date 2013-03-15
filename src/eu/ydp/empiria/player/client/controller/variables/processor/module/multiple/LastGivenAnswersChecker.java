package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import java.util.List;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;

public class LastGivenAnswersChecker {

	public boolean isAnyAnswerCorrect(List<String> answers, CorrectAnswers correctAnswers){
		int amountOfCorrectAnswers = countHowManyOfAnswersAreCorrect(answers, correctAnswers);
		boolean isAnyCorrect = amountOfCorrectAnswers > 0;
		return isAnyCorrect;
	}
	
	private int countHowManyOfAnswersAreCorrect(List<String> answers, CorrectAnswers correctAnswers){
		int countOfCorrectAnswers = 0;
		for (String answer : answers) {
			if(correctAnswers.containsAnswer(answer)){
				countOfCorrectAnswers++;
			}
		}
		return countOfCorrectAnswers;
	}
}
