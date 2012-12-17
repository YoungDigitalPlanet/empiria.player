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
import eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor;


public class ResultInfoJUnitTest extends AbstractTestBase{
	
	@Test
	public void shouldCreateResult() {
		AssessmentReportFactory factory = injector.getInstance(AssessmentReportFactory.class);
		VariableProviderSocket variableProvider = getVariableProvider();
		ResultInfo result = factory.getResultInfo(variableProvider);
		
		assertThat("todo", result.getTodo(), is(equalTo(2)));
		assertThat("done", result.getDone(), is(equalTo(1)));
		assertThat("errors", result.getErrors(), is(equalTo(3)));
	}
	
	private VariableProviderSocket getVariableProvider(){
		VariableProviderSocket variableProvider = mock(VariableProviderSocket.class);
		OutcomeCreator crator = new OutcomeCreator();
		
		when(variableProvider.getVariableValue(DefaultVariableProcessor.TODO)).thenReturn(crator.createTodoOutcome(2));
		when(variableProvider.getVariableValue(DefaultVariableProcessor.DONE)).thenReturn(crator.createDoneOutcome(1));
		when(variableProvider.getVariableValue(DefaultVariableProcessor.ERRORS)).thenReturn(crator.createErrorsOutcome(3));
		
		return variableProvider;
	}

}
