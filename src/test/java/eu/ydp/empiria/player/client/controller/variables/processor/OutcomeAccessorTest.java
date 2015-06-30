package eu.ydp.empiria.player.client.controller.variables.processor;

import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OutcomeAccessorTest {

    private static final int PAGE_INDEX = 8;

    @InjectMocks
    private OutcomeAccessor accessor;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private SessionDataSupplier sessionDataSupplier;
    @Mock
    private FlowDataSupplier flowDataSupplier;
    @Mock
    private OutcomesResultCalculator resultCalculator;

    VariableProviderSocket itemVariableProviderSocket;
    VariableProviderSocket assessmentVariableProviderSocket;

    @Before
    public void setUp() {
        when(flowDataSupplier.getCurrentPageIndex()).thenReturn(PAGE_INDEX);
        itemVariableProviderSocket = sessionDataSupplier.getItemSessionDataSocket(eq(PAGE_INDEX)).getVariableProviderSocket();
        assessmentVariableProviderSocket = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
    }

    @Test
    public void getCurrentPageTodo() {
        final Integer TODO = 5;
        // given
        mockItemTodo(TODO);

        // when
        int todo = accessor.getCurrentPageTodo();

        // then
        assertThat(todo).isEqualTo(TODO);
    }

    @Test
    public void getCurrentPageDone() {
        final Integer DONE = 6;
        // given
        mockItemDone(DONE);

        // when
        int done = accessor.getCurrentPageDone();

        // then
        assertThat(done).isEqualTo(DONE);
    }

    @Test
    public void getCurrentPageErrors() {
        final Integer ERRORS = 7;
        // given
        mockItemErrors(ERRORS);

        // when
        int errors = accessor.getCurrentPageErrors();

        // then
        assertThat(errors).isEqualTo(ERRORS);
    }

    @Test
    public void getCurrentPageMistakes() {
        final Integer MISTAKES = 8;
        // given
        mockItemMistakes(MISTAKES);

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
    public void getAssessmentTodo() {
        final Integer TODO = 5;
        // given
        mockAssessmentTodo(TODO);

        // when
        int todo = accessor.getAssessmentTodo();

        // then
        assertThat(todo).isEqualTo(TODO);
    }

    @Test
    public void getAssessmentDone() {
        final Integer DONE = 6;
        // given
        mockAssessmentDone(DONE);

        // when
        int done = accessor.getAssessmentDone();

        // then
        assertThat(done).isEqualTo(DONE);
    }

    @Test
    public void getAssessmentErrors() {
        final Integer ERRORS = 7;
        // given
        mockAssessmentErrors(ERRORS);

        // when
        int errors = accessor.getAssessmentErrors();

        // then
        assertThat(errors).isEqualTo(ERRORS);
    }

    @Test
    public void getAssessmentMistakes() {
        final Integer MISTAKES = 8;
        // given
        mockAssessmentMistakes(MISTAKES);

        // when
        int mistakes = accessor.getAssessmentMistakes();

        // then
        assertThat(mistakes).isEqualTo(MISTAKES);
    }

    @Test
    public void getAssessmentResult() {
        // given
        mockAssessmentTodo(73);
        mockAssessmentDone(32);
        when(resultCalculator.calculateResult(73, 32)).thenReturn(43);

        // when
        int mistakes = accessor.getAssessmentResult();

        // then
        assertThat(mistakes).isEqualTo(43);
    }

    @Test
    public void getAssessmentResult_zero() {
        // given
        mockAssessmentTodo(73);
        mockAssessmentDone(0);
        when(resultCalculator.calculateResult(73, 0)).thenReturn(0);

        // when
        int mistakes = accessor.getAssessmentResult();

        // then
        assertThat(mistakes).isEqualTo(0);
    }

    @Test
    public void getAssessmentResult_completed() {
        // given
        mockAssessmentTodo(73);
        mockAssessmentDone(73);
        when(resultCalculator.calculateResult(73, 73)).thenReturn(100);

        // when
        int mistakes = accessor.getAssessmentResult();

        // then
        assertThat(mistakes).isEqualTo(100);
    }

    private void mockItemTodo(Integer TODO) {
        when(itemVariableProviderSocket.getVariableValue(eq("TODO")).getValuesShort()).thenReturn(TODO.toString());
    }

    private void mockItemDone(Integer DONE) {
        when(itemVariableProviderSocket.getVariableValue(eq("DONE")).getValuesShort()).thenReturn(DONE.toString());
    }

    private void mockItemErrors(Integer ERRORS) {
        when(itemVariableProviderSocket.getVariableValue(eq("ERRORS")).getValuesShort()).thenReturn(ERRORS.toString());
    }

    private void mockItemMistakes(Integer MISTAKES) {
        when(itemVariableProviderSocket.getVariableValue(eq("MISTAKES")).getValuesShort()).thenReturn(MISTAKES.toString());
    }

    private void mockAssessmentTodo(Integer TODO) {
        when(assessmentVariableProviderSocket.getVariableValue(eq("TODO")).getValuesShort()).thenReturn(TODO.toString());
    }

    private void mockAssessmentDone(Integer DONE) {
        when(assessmentVariableProviderSocket.getVariableValue(eq("DONE")).getValuesShort()).thenReturn(DONE.toString());
    }

    private void mockAssessmentErrors(Integer ERRORS) {
        when(assessmentVariableProviderSocket.getVariableValue(eq("ERRORS")).getValuesShort()).thenReturn(ERRORS.toString());
    }

    private void mockAssessmentMistakes(Integer MISTAKES) {
        when(assessmentVariableProviderSocket.getVariableValue(eq("MISTAKES")).getValuesShort()).thenReturn(MISTAKES.toString());
    }
}
