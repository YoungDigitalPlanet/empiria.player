package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.DoneToCountModeAdjuster;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.ErrorsToCountModeAdjuster;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupedModeVariableProcessorJUnitTest {

	private GroupedModeVariableProcessor groupedModeVariableProcessor;

	@Mock
	private GroupedAnswersManager groupedAnswersManager;
	@Mock
	private ErrorsToCountModeAdjuster errorsToCountModeAdjuster;
	@Mock
	private DoneToCountModeAdjuster doneToCountModeAdjuster;

	private final CountMode countMode = CountMode.SINGLE;

	@Before
	public void setUp() throws Exception {
		groupedModeVariableProcessor = new GroupedModeVariableProcessor(groupedAnswersManager, errorsToCountModeAdjuster, doneToCountModeAdjuster);
	}

	@Test
	public void shouldCalculateErrors() throws Exception {
		String answer = "answer";
		Response response = builder().withCurrentUserAnswers(answer)
				.withGroups("group1")
				.build();

		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups(answer, response, response.groups)).thenReturn(false);

		when(errorsToCountModeAdjuster.adjustValueToCountMode(1, countMode)).thenReturn(1);

		int amountOfErrors = groupedModeVariableProcessor.calculateErrors(response);

		assertThat(amountOfErrors, equalTo(1));
	}

	@Test
	public void shouldCalculateDone() throws Exception {
		Response response = builder().withCorrectAnswers("doneAnswer1")
				.withCurrentUserAnswers("doneAnswer1", "wrongAnswer")
				.withGroups("group1")
				.build();

		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups("wrongAnswer", response, response.groups))
			.thenReturn(false);

		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups("doneAnswer1", response, response.groups))
			.thenReturn(true);

		when(doneToCountModeAdjuster.adjustValueToCountMode(1, response, countMode))
			.thenReturn(1);

		int done = groupedModeVariableProcessor.calculateDone(response);
		assertThat(done, equalTo(1));
	}
	
	@Test
	public void shouldSetLastmistakenBecauseNotCorrectAnswersAdded() throws Exception {		
		Response response = builder().withCorrectAnswers("doneAnswer1")
				.withGroups("group1")
				.build();
		
		List<String> addedAnswers = Lists.newArrayList("wrongAnswer");
		@SuppressWarnings("unchecked")
		LastAnswersChanges answersChanges = new LastAnswersChanges(addedAnswers, Collections.EMPTY_LIST);
		
		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups("wrongAnswer", response, response.groups))
			.thenReturn(false);
		
		LastMistaken lastmistaken = groupedModeVariableProcessor.checkLastmistaken(response, answersChanges);
		
		assertThat(lastmistaken, equalTo(LastMistaken.WRONG));
	}

	@Test
	public void shouldNotSetLastmistakenBecauseAnyAnswerWasChanged() throws Exception {		
		Response response = builder().withCorrectAnswers("doneAnswer1")
				.withGroups("group1")
				.build();
		
		@SuppressWarnings("unchecked")
		LastAnswersChanges answersChanges = new LastAnswersChanges(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
		
		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups("wrongAnswer", response, response.groups))
		.thenReturn(false);
		
		LastMistaken lastmistaken = groupedModeVariableProcessor.checkLastmistaken(response, answersChanges);
		
		assertThat(lastmistaken, equalTo(LastMistaken.NONE));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotSetLastmistakenBecauseCorrectAnswersAdded() throws Exception {		
		Response response = builder().withCorrectAnswers("doneAnswer1")
				.withGroups("group1")
				.build();
		
		List<String> addedAnswers = Lists.newArrayList("doneAnswer1");
		LastAnswersChanges answersChanges = new LastAnswersChanges(addedAnswers, Collections.EMPTY_LIST);
		
		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups("doneAnswer1", response, response.groups))
		.thenReturn(true);
		
		LastMistaken lastmistaken = groupedModeVariableProcessor.checkLastmistaken(response, answersChanges);
		
		assertThat(lastmistaken, equalTo(LastMistaken.CORRECT));
	}
	
	@Test
	public void shouldCalculateMistakesWhenLastAnswerWasMistake() throws Exception {
		LastMistaken lastmistaken = LastMistaken.WRONG;
		int previousMistakes = 123;
		
		int newMistakes = groupedModeVariableProcessor.calculateMistakes(lastmistaken, previousMistakes);
		
		assertThat(newMistakes, equalTo(previousMistakes + 1));
	}
	
	@Test
	public void shouldCalculateMistakesWhenLastAnswerWasCorrect() throws Exception {
		LastMistaken lastmistaken = LastMistaken.CORRECT;
		int previousMistakes = 123;
		
		int newMistakes = groupedModeVariableProcessor.calculateMistakes(lastmistaken, previousMistakes);
		
		assertThat(newMistakes, equalTo(previousMistakes));
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void shouldRecognizeNotCorrectTypeOfAnswersEvaluation() throws Exception {
		Response response = builder().withEvaluate(Evaluate.CORRECT)
				.build();
		
		groupedModeVariableProcessor.evaluateAnswers(response);
	}
	
	@Test
	public void shouldEvaluateUserAnswers() throws Exception {
		//given
		Response response = builder().withCorrectAnswers("doneAnswer1")
				.withCurrentUserAnswers("doneAnswer1", "wrongAnswer")
				.withGroups("group1")
				.build();
		
		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups("doneAnswer1", response, response.groups))
		.thenReturn(true);

		when(groupedAnswersManager.isAnswerCorrectInAnyOfGroups("wrongAnswer", response, response.groups))
			.thenReturn(false);
		
		//when
		List<Boolean> answersEvaluations = groupedModeVariableProcessor.evaluateAnswers(response);
		
		//then
		List<Boolean> expectedEvaluation = Arrays.asList(true, false);
		assertThat(answersEvaluations, equalTo(expectedEvaluation));
	}

	private ResponseBuilder builder() {
		return new ResponseBuilder().withCountMode(countMode);
	}
}
