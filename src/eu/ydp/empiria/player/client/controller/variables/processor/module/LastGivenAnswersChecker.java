package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.List;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;

public class LastGivenAnswersChecker {

	public boolean isAnyAddedAnswerNotCorrect(List<String> addedAnswers, CorrectAnswers correctAnswers){
		int countHowManyOfAnswersAreCorrect = countHowManyOfAnswersAreCorrect(addedAnswers, correctAnswers);
		boolean isAnyAddedAnswerNotCorrect = addedAnswers.size() > countHowManyOfAnswersAreCorrect;
		return isAnyAddedAnswerNotCorrect;
	}
	
	public boolean isAnyRemovedAnswerCorrect(List<String> removedAnswers, CorrectAnswers correctAnswers){
		int countHowManyOfAnswersAreCorrect = countHowManyOfAnswersAreCorrect(removedAnswers, correctAnswers);
		boolean isAnyRemovedAnswerCorrect = countHowManyOfAnswersAreCorrect > 0;
		return isAnyRemovedAnswerCorrect;
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
