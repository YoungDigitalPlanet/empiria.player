package eu.ydp.empiria.player.client.controller.variables.processor;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static junitparams.JUnitParamsRunner.$;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitParamsRunner.class)
public class FeedbackActionConditionsTest {

	@InjectMocks
	private FeedbackActionConditions feedbackActionConditions;
	@Mock
	private OutcomeAccessor outcomeAccessor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Parameters(method = "pageAllOkParameters")
	public void shouldReturnPageAllOk(int todo, int done, boolean expectedResult) {
		// given
		mockTodo(todo);
		mockDone(done);

		// when
		boolean pageAllOk = feedbackActionConditions.isPageAllOk();

		// then
		assertThat(pageAllOk).isEqualTo(expectedResult);
	}

	private Object[] pageAllOkParameters() {
		return $($(5, 5, true), $(5, 6, false), $(0, 6, false));
	}

	@Test
	@Parameters(method = "pageAllOkWithoutPreviousMistakesParameters")
	public void shouldReturnPageAllOkWithoutPreviousMistakes(int todo, int done, int mistakes, boolean expectedResult) {
		// given
		mockTodo(todo);
		mockDone(done);
		mockMistakes(mistakes);

		// when
		boolean pageAllOkWithoutPreviousMistakes = feedbackActionConditions.isPageAllOkWithoutPreviousMistakes();

		// then
		assertThat(pageAllOkWithoutPreviousMistakes).isEqualTo(expectedResult);
	}

	private Object[] pageAllOkWithoutPreviousMistakesParameters() {
		return $($(5, 5, 0, true), $(5, 5, 1, false), $(5, 4, 1, false), $(5, 4, 0, false));
	}

	private void mockTodo(Integer TODO) {
		when(outcomeAccessor.getCurrentPageTodo()).thenReturn(TODO);
	}

	private void mockDone(Integer DONE) {
		when(outcomeAccessor.getCurrentPageDone()).thenReturn(DONE);
	}

	private void mockMistakes(Integer MISTAKES) {
		when(outcomeAccessor.getCurrentPageMistakes()).thenReturn(MISTAKES);
	}
}
