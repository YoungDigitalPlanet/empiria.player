package eu.ydp.empiria.player.client.controller.variables.processor.module.counting.predicates;

import com.google.gwt.thirdparty.guava.common.base.Predicate;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;

public class WrongAnswerPredicate implements Predicate<String> {

	private CorrectAnswers correctAnswers;
	
	public WrongAnswerPredicate(CorrectAnswers correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	@Override
	public boolean apply(String answer) {
		boolean isWrongAnswer = !correctAnswers.containsAnswer(answer);
		return isWrongAnswer;
	}

}
