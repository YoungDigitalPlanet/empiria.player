package eu.ydp.empiria.player.client.controller.report;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

public class HintInfoJUnitTest extends AbstractTestBase {

	@Test
	public void shouldCreateHints() {
		AssessmentReportFactory factory = injector.getInstance(AssessmentReportFactory.class);
		VariableProviderSocket variableProvider = getVariableProvider();
		HintInfo hints = factory.getHintInfo(variableProvider);

		assertThat("checks", hints.getChecks(), is(equalTo(3)));
		assertThat("mistakes", hints.getMistakes(), is(equalTo(4)));
		assertThat("showAnswers", hints.getShowAnswers(), is(equalTo(5)));
		assertThat("reset", hints.getReset(), is(equalTo(6)));
	}

	private VariableProviderSocket getVariableProvider() {
		VariableProviderSocket variableProvider = mock(VariableProviderSocket.class);
		OutcomeCreator crator = new OutcomeCreator();

		when(variableProvider.getVariableValue(FlowActivityVariablesProcessor.CHECKS)).thenReturn(crator.createTodoOutcome(3));
		when(variableProvider.getVariableValue(VariableName.MISTAKES.toString())).thenReturn(crator.createDoneOutcome(4));
		when(variableProvider.getVariableValue(FlowActivityVariablesProcessor.SHOW_ANSWERS)).thenReturn(crator.createErrorsOutcome(5));
		when(variableProvider.getVariableValue(FlowActivityVariablesProcessor.RESET)).thenReturn(crator.createErrorsOutcome(6));

		return variableProvider;
	}

}
