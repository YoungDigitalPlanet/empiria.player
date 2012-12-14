package eu.ydp.empiria.player.client.controller.variables;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class VariableUtilTest {
	
	@Test
	public void shouldReturnVariableValue() {
		VariableProviderSocket variableProviderSocket = mock(VariableProviderSocket.class);
		Variable variable = mock(Variable.class);
		VariableUtil util = new VariableUtil(variableProviderSocket);

		when(variable.getValuesShort()).thenReturn("3");
		when(variableProviderSocket.getVariableValue("existingVariable")).thenReturn(variable);

		assertThat(util.getVariableValue("notExistingName", "0"), is(equalTo("0")));
		assertThat(util.getVariableValue("existingVariable", "0"), is(equalTo("3")));
	}

}
