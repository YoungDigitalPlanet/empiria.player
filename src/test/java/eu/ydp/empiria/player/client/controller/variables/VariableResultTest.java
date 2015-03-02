package eu.ydp.empiria.player.client.controller.variables;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.TODO;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;

public class VariableResultTest {

	@Test
	public void shouldCountResult() {
		assertThat(new VariableResult(0, 10).getResult(), is(equalTo(0)));
		assertThat(new VariableResult(2, 0).getResult(), is(equalTo(0)));
		assertThat(new VariableResult(25, 50).getResult(), is(equalTo(50)));
		assertThat(new VariableResult(1, 3).getResult(), is(equalTo(33)));
		assertThat(new VariableResult(2, 3).getResult(), is(equalTo(66)));
		assertThat(new VariableResult(10, 10).getResult(), is(equalTo(100)));
	}

	@Test
	public void shouldReturResulFromVariableProvider() {
		VariableProviderSocket variableProvider = mock(VariableProviderSocket.class);
		OutcomeCreator outcomeCreator = new OutcomeCreator();

		when(variableProvider.getVariableValue(TODO.toString())).thenReturn(outcomeCreator.createTodoOutcome(3));
		when(variableProvider.getVariableValue(DONE.toString())).thenReturn(outcomeCreator.createDoneOutcome(1));

		VariableResult result = new VariableResult(variableProvider);
		assertThat(result.getResult(), is(equalTo(33)));
	}

}
