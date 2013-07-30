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
	public void shouldRecognizeThatAnswerIsIncorrect() throws Exception {
		List<String> answers = Lists.newArrayList("correct","incorrect");
		CorrectAnswers correctAnswers = createCorrectAnswers("correct");
		
		boolean anyAnswerIncorrect = lastGivenAnswersChecker.isAnyAsnwerIncorrect(answers, correctAnswers);
		
		assertThat(anyAnswerIncorrect, equalTo(true));
	}

	@Test
	public void shouldRecognizeThatAllAnswersAreWrong() throws Exception {
		List<String> answers = Lists.newArrayList("correct1", "correct2");
		CorrectAnswers correctAnswers = createCorrectAnswers("correct1", "correct2");
		
		boolean anyAnswerIncorrect = lastGivenAnswersChecker.isAnyAsnwerIncorrect(answers, correctAnswers);
		
		assertThat(anyAnswerIncorrect, equalTo(false));
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
