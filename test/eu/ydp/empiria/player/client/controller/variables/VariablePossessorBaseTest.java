package eu.ydp.empiria.player.client.controller.variables;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

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
}
