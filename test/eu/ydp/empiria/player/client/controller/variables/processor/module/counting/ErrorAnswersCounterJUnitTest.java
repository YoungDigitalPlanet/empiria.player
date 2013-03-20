package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class ErrorAnswersCounterJUnitTest {

	private ErrorAnswersCounter errorAnswersCounter;
	private GeneralAnswersCounter generalAnswersCounter;
	private ErrorsToCountModeAdjuster errorsToCountModeAdjuster;

	@Before
	public void setUp() throws Exception {
		errorsToCountModeAdjuster = new ErrorsToCountModeAdjuster();
		generalAnswersCounter = new GeneralAnswersCounter();
		errorAnswersCounter = new ErrorAnswersCounter(generalAnswersCounter, errorsToCountModeAdjuster);
	}

	@Test
	public void shouldCountMoreThanOneErrorAsOneErrorInSingleCountMode() throws Exception {

		Response response = new ResponseBuilder().withCorrectAnswers("correct1", "correct2")
				.withCurrentUserAnswers("wrong", "wrong2")
				.withCountMode(CountMode.SINGLE)
				.build();

		int amountOfErrors = errorAnswersCounter.countErrorAnswersAdjustedToMode(response);
		
		assertThat(amountOfErrors, equalTo(1));
	}
	
	@Test
	public void shouldCountAllCorrectAnswersAsNoErrors() throws Exception {
		
		Response response = new ResponseBuilder().withCorrectAnswers("correct1", "correct2")
				.withCurrentUserAnswers("correct2", "correct1")
				.withCountMode(CountMode.SINGLE)
				.build();
		
		int amountOfErrors = errorAnswersCounter.countErrorAnswersAdjustedToMode(response);
		
		assertThat(amountOfErrors, equalTo(0));
	}
	
	@Test
	public void shouldCountExactAmountOfErrorsInCorrectAnswersCountMode() throws Exception {
		
		Response response = new ResponseBuilder().withCorrectAnswers("correct1", "correct2")
				.withCurrentUserAnswers("error1", "error2")
				.withCountMode(CountMode.CORRECT_ANSWERS)
				.build();
		
		int amountOfErrors = errorAnswersCounter.countErrorAnswersAdjustedToMode(response);
		
		assertThat(amountOfErrors, equalTo(2));
	}

}
