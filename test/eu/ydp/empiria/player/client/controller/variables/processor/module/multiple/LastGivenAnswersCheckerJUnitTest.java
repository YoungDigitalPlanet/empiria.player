package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import java.util.List;

import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;


public class LastGivenAnswersCheckerJUnitTest {

	private LastGivenAnswersChecker lastGivenAnswersChecker = new LastGivenAnswersChecker();
	
	@Test
	public void shouldRecognizeThatAnswerIsCorrect() throws Exception {
		List<String> answers = Lists.newArrayList("wrong", "correct");
		CorrectAnswers correctAnswers = createCorrectAnswers("correct");
		
		boolean anyAnswerCorrect = lastGivenAnswersChecker.isAnyAnswerCorrect(answers, correctAnswers);
		
		assertThat(anyAnswerCorrect, equalTo(true));
	}

	@Test
	public void shouldRecognizeThatAllAnswersAreWrong() throws Exception {
		List<String> answers = Lists.newArrayList("wrong", "wrong2");
		CorrectAnswers correctAnswers = createCorrectAnswers("correct");
		
		boolean anyAnswerCorrect = lastGivenAnswersChecker.isAnyAnswerCorrect(answers, correctAnswers);
		
		assertThat(anyAnswerCorrect, equalTo(false));
	}

	private CorrectAnswers createCorrectAnswers(String ... answers) {
		CorrectAnswers correctAnswers = new CorrectAnswers();
		for (String correctAnswer : answers) {
			ResponseValue responseValue = new ResponseValue(correctAnswer);
			correctAnswers.add(responseValue);
		}
		return correctAnswers;
	}
	
}
