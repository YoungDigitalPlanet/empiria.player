package eu.ydp.empiria.player.client.controller.variables;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VariablePossessorBaseTest {

    VariablePossessorBase<Variable> variablePossessor;

    @Before
    public void setUp() throws Exception {
        variablePossessor = new VariablePossessorBaseMock<Variable>();
    }

    @Test
    public void lastAnswerSelectAction_noVariables() {
        // given
        variablePossessor.variables = new ImmutableMap.Builder<String, Variable>().build();

        // when
        boolean lastAnswerSelectAction = variablePossessor.isLastAnswerSelectAction();

        // then
        assertThat(lastAnswerSelectAction).isFalse();
    }

    @Test
    public void lastAnswerSelectAction_noMatchingVariable() {
        // given
        Variable variableMock = mock(Variable.class);
        variablePossessor.variables = new ImmutableMap.Builder<String, Variable>().put("RESP_1-TODO", variableMock).build();

        // when
        boolean lastAnswerSelectAction = variablePossessor.isLastAnswerSelectAction();

        // then
        assertThat(lastAnswerSelectAction).isFalse();
    }

    @Test
    public void lastAnswerSelectAction_matchingVariableSelected() {
        // given
        Variable variableMock = mock(Variable.class);
        variableMock.values = Lists.newArrayList("+RESP_1_0");
        variablePossessor.variables = new ImmutableMap.Builder<String, Variable>().put("RESP_1_LASTCHANGE", variableMock).build();

        // when
        boolean lastAnswerSelectAction = variablePossessor.isLastAnswerSelectAction();

        // then
        assertThat(lastAnswerSelectAction).isTrue();
    }

    @Test
    public void lastAnswerSelectAction_matchingVariableUnselected() {
        // given
        Variable variableMock = mock(Variable.class);
        variableMock.values = Lists.newArrayList("-RESP_1_0");
        variablePossessor.variables = new ImmutableMap.Builder<String, Variable>().put("RESP_1_LASTCHANGE", variableMock).build();

        // when
        boolean lastAnswerSelectAction = variablePossessor.isLastAnswerSelectAction();

        // then
        assertThat(lastAnswerSelectAction).isFalse();
    }

    @Test
    public void lastAnswerSelectAction_matchingVariablePlus() {
        // given
        Variable variableMock = mock(Variable.class);
        variableMock.values = Lists.newArrayList("+");
        variablePossessor.variables = new ImmutableMap.Builder<String, Variable>().put("RESP_1_LASTCHANGE", variableMock).build();

        // when
        boolean lastAnswerSelectAction = variablePossessor.isLastAnswerSelectAction();

        // then
        assertThat(lastAnswerSelectAction).isFalse();
    }

    class VariablePossessorBaseMock<V extends Variable> extends VariablePossessorBase<V> {
    }

    @Test
    public void shouldResetEveryVariable() {
        // given
        Variable variableMock1 = mock(Variable.class);
        Variable variableMock2 = mock(Variable.class);
        variablePossessor.variables = new ImmutableMap.Builder<String, Variable>().put("MOCK_1", variableMock1).put("MOCK_2", variableMock2).build();

        // when
        variablePossessor.reset();

        // then
        verify(variableMock1).reset();
        verify(variableMock2).reset();
    }
}
