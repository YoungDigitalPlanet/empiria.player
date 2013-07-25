package eu.ydp.empiria.player.client.controller.variables.processor;

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
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

@RunWith(MockitoJUnitRunner.class)
public class OutcomeAccessorTest {

	private static final int PAGE_INDEX = 8;

	@InjectMocks
	private OutcomeAccessor accessor;
	
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SessionDataSupplier sessionDataSupplier;
	@Mock
	private FlowDataSupplier flowDataSupplier;
	
	@Before
	public void setUp() {
		when(flowDataSupplier.getCurrentPageIndex()).thenReturn(PAGE_INDEX);
	}
	
	@Test
	public void getCurrentPageTodo() {
		final Integer TODO = 5;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("TODO").getValuesShort()).thenReturn(TODO.toString());
		
		// when
		int todo = accessor.getCurrentPageTodo();
		
		// then
		assertThat(todo).isEqualTo(TODO);
	}
	
	@Test
	public void getCurrentPageDone() {
		final Integer DONE = 6;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("DONE").getValuesShort()).thenReturn(DONE.toString());
		
		// when
		int done = accessor.getCurrentPageDone();
		
		// then
		assertThat(done).isEqualTo(DONE);
	}
	
	@Test
	public void getCurrentPageErrors() {
		final Integer ERRORS = 7;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("ERRORS").getValuesShort()).thenReturn(ERRORS.toString());
		
		// when
		int errors = accessor.getCurrentPageErrors();
		
		// then
		assertThat(errors).isEqualTo(ERRORS);
	}
	
	@Test
	public void getCurrentPageMistakes() {
		final Integer MISTAKES = 8;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("MISTAKES").getValuesShort()).thenReturn(MISTAKES.toString());
		
		// when
		int mistakes = accessor.getCurrentPageMistakes();
		
		// then
		assertThat(mistakes).isEqualTo(MISTAKES);
	}
	
	@Test
	public void getCurrentPageLastMistaken_false() {
		final boolean LAST_MISTAKEN = false;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("LASTMISTAKEN").getValuesShort()).thenReturn("0");
		
		// when
		boolean lastMistaken = accessor.isCurrentPageLastMistaken();
		
		// then
		assertThat(lastMistaken).isEqualTo(LAST_MISTAKEN);
	}
	
	@Test
	public void getCurrentPageLastMistaken_true() {
		final boolean LAST_MISTAKEN = true;
		// given
		when(sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket().getVariableValue("LASTMISTAKEN").getValuesShort()).thenReturn("1");
		
		// when
		boolean lastMistaken = accessor.isCurrentPageLastMistaken();
		
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
	
}
