package eu.ydp.empiria.player.client.controller.variables.processor;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.CORRECT;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.NONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.WRONG;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;

@RunWith(MockitoJUnitRunner.class)
public class OutcomeAccessorTest {

	private static final int PAGE_INDEX = 8;

	@InjectMocks
	private OutcomeAccessor accessor;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SessionDataSupplier sessionDataSupplier;
	@Mock
	private FlowDataSupplier flowDataSupplier;
	VariableProviderSocket variableProviderSocket;

	@Before
	public void setUp() {
		when(flowDataSupplier.getCurrentPageIndex()).thenReturn(PAGE_INDEX);
		variableProviderSocket = sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket();
	}

	@Test
	public void getCurrentPageTodo() {
		final Integer TODO = 5;
		// given
		mockTodo(TODO);

		// when
		int todo = accessor.getCurrentPageTodo();

		// then
		assertThat(todo).isEqualTo(TODO);
	}

	@Test
	public void getCurrentPageDone() {
		final Integer DONE = 6;
		// given
		mockDone(DONE);

		// when
		int done = accessor.getCurrentPageDone();

		// then
		assertThat(done).isEqualTo(DONE);
	}

	@Test
	public void getCurrentPageErrors() {
		final Integer ERRORS = 7;
		// given
		mockErrors(ERRORS);

		// when
		int errors = accessor.getCurrentPageErrors();

		// then
		assertThat(errors).isEqualTo(ERRORS);
	}

	@Test
	public void getCurrentPageMistakes() {
		final Integer MISTAKES = 8;
		// given
		mockMistakes(MISTAKES);

		// when
		int mistakes = accessor.getCurrentPageMistakes();

		// then
		assertThat(mistakes).isEqualTo(MISTAKES);
	}

	@Test
	public void getCurrentPageLastMistaken_correct() {
		final LastMistaken LAST_MISTAKEN = CORRECT;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("LASTMISTAKEN").getValuesShort())
				.thenReturn("CORRECT");

		// when
		LastMistaken lastMistaken = accessor.getCurrentPageLastMistaken();

		// then
		assertThat(lastMistaken).isEqualTo(LAST_MISTAKEN);
	}

	@Test
	public void getCurrentPageLastMistaken_none() {
		final LastMistaken LAST_MISTAKEN = NONE;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("LASTMISTAKEN").getValuesShort())
				.thenReturn("NONE");

		// when
		LastMistaken lastMistaken = accessor.getCurrentPageLastMistaken();

		// then
		assertThat(lastMistaken).isEqualTo(LAST_MISTAKEN);
	}

	@Test
	public void getCurrentPageLastMistaken_wrong() {
		final LastMistaken LAST_MISTAKEN = WRONG;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("LASTMISTAKEN").getValuesShort())
				.thenReturn("WRONG");

		// when
		LastMistaken lastMistaken = accessor.getCurrentPageLastMistaken();

		// then
		assertThat(lastMistaken).isEqualTo(LAST_MISTAKEN);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void isLastActionSelection_true() {
		// given
		VariablePossessorBase<Variable> variablePossessorBase = mock(VariablePossessorBase.class);
		when(variablePossessorBase.isLastAnswerSelectAction()).thenReturn(true);
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket()).thenReturn(variablePossessorBase);

		// when
		boolean lastActionSelection = accessor.isLastActionSelection();

		// then
		assertThat(lastActionSelection).isEqualTo(true);
		verify(variablePossessorBase).isLastAnswerSelectAction();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void isLastActionSelection_false() {
		// given
		VariablePossessorBase<Variable> variablePossessorBase = mock(VariablePossessorBase.class);
		when(variablePossessorBase.isLastAnswerSelectAction()).thenReturn(false);
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket()).thenReturn(variablePossessorBase);

		// when
		boolean lastActionSelection = accessor.isLastActionSelection();

		// then
		assertThat(lastActionSelection).isEqualTo(false);
		verify(variablePossessorBase).isLastAnswerSelectAction();
	}

	@Test
	public void isLastActionSelection_variableProviderSocketIsNotInstanceOfVariablePossessorBase() {
		// when
		boolean lastActionSelection = accessor.isLastActionSelection();

		// then
		assertThat(lastActionSelection).isEqualTo(false);
	}

	@Test
	public void shouldReturnPageAllOk_todoEqDone() {
		// given
		mockTodo(5);
		mockDone(5);

		// when
		boolean pageAllOk = accessor.isPageAllOk();

		// then
		assertThat(pageAllOk).isTrue();
	}

	@Test
	public void shouldReturnPageAllOk_todoNotEqDone() {
		// given
		mockTodo(5);
		mockDone(6);

		// when
		boolean pageAllOk = accessor.isPageAllOk();

		// then
		assertThat(pageAllOk).isFalse();
	}

	@Test
	public void shouldReturnPageAllOk_todoIsZero() {
		// given
		mockTodo(0);
		mockDone(6);

		// when
		boolean pageAllOk = accessor.isPageAllOk();

		// then
		assertThat(pageAllOk).isFalse();
	}

	@Test
	public void shouldReturnPageAllOkWithoutPreviousMistakes_pageAllOkWithoutMistakes() {
		// given
		mockTodo(5);
		mockDone(5);
		mockMistakes(0);

		// when
		boolean pageAllOkWithoutPreviousMistakes = accessor.isPageAllOkWithoutPreviousMistakes();

		// then
		assertThat(pageAllOkWithoutPreviousMistakes).isTrue();
	}

	@Test
	public void shouldReturnPageAllOkWithoutPreviousMistakes_pageAllOkWithMistakes() {
		// given
		mockTodo(5);
		mockDone(5);
		mockMistakes(1);

		// when
		boolean pageAllOkWithoutPreviousMistakes = accessor.isPageAllOkWithoutPreviousMistakes();

		// then
		assertThat(pageAllOkWithoutPreviousMistakes).isFalse();
	}

	private void mockTodo(Integer TODO) {
		when(variableProviderSocket.getVariableValue(eq("TODO")).getValuesShort()).thenReturn(TODO.toString());
	}

	private void mockDone(Integer DONE) {
		when(variableProviderSocket.getVariableValue(eq("DONE")).getValuesShort()).thenReturn(DONE.toString());
	}

	private void mockErrors(Integer ERRORS) {
		when(variableProviderSocket.getVariableValue(eq("ERRORS")).getValuesShort()).thenReturn(ERRORS.toString());
	}

	private void mockMistakes(Integer MISTAKES) {
		when(variableProviderSocket.getVariableValue(eq("MISTAKES")).getValuesShort()).thenReturn(MISTAKES.toString());
	}

}
