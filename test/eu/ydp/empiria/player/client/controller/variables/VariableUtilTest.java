package eu.ydp.empiria.player.client.controller.variables;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class VariableUtilTest {

	private VariableUtil util;

	@Before
	public void initialize() {
		VariableProviderSocket variableProviderSocket = getVariableProvider();
		util = new VariableUtil(variableProviderSocket);
	}

	@Test
	public void shouldReturnVariableValue() {
		assertThat(util.getVariableValue("notExistingName", "0"), is(equalTo("0")));
		assertThat(util.getVariableValue("existingVariable", "0"), is(equalTo("3")));
	}

	@Test
	public void shouldReturnVariableIntValue() {
		assertThat(util.getVariableIntValue("notExistingName", 0), is(equalTo(0)));
		assertThat(util.getVariableIntValue("existingVariable", 0), is(equalTo(3)));
	}

	private VariableProviderSocket getVariableProvider() {
		VariableProviderSocket variableProviderSocket = mock(VariableProviderSocket.class);
		Variable variable = mock(Variable.class);

		when(variable.getValuesShort()).thenReturn("3");
		when(variableProviderSocket.getVariableValue("existingVariable")).thenReturn(variable);

		return variableProviderSocket;
	}

}
