package eu.ydp.empiria.player.client.controller.variables.processor;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;

public class AnswerEvaluationSupplierTest {

	private Logger LOGGER = Logger.getLogger(VariableProcessingAdapter.class.getName());
	
	private AnswerEvaluationSupplier supplier = new AnswerEvaluationSupplier();
	
	@Test
	public void evaluateAnswer() {
		// given
		final String ID = "";
		Response response = createResponse(ID);
		List<Boolean> answerEvaluations = Lists.newArrayList(true, false, true);		
		ModulesProcessingResults modulesProcessingResults = prepareModulesProcessingResults(ID, answerEvaluations);
		supplier.updateModulesProcessingResults(modulesProcessingResults );
		
		// when
		List<Boolean> evaluation = supplier.evaluateAnswer(response);
		
		// then
		assertThat(evaluation).isEqualTo(answerEvaluations);
	}

	private Response createResponse(final String ID) {
		Response response = mock(Response.class);
		when(response.getID()).thenReturn(ID);
		return response;
	}

	private ModulesProcessingResults prepareModulesProcessingResults(final String ID, List<Boolean> answerEvaluations) {
		ModulesProcessingResults modulesProcessingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
		DtoModuleProcessingResult results = modulesProcessingResults.getProcessingResultsForResponseId(ID);
		results.setGeneralVariables(new GeneralVariables(Lists.<String>newArrayList(), answerEvaluations, 0, 0));
		return modulesProcessingResults;
	}
	
	@Test
	public void evaluateAnswer_evaluationBeforeAnswerProcessing() {
		// given
		Response response = mock(Response.class);
		Handler handler = mock(Handler.class);
		LOGGER.addHandler(handler);
		
		// when
		List<Boolean> evaluation = supplier.evaluateAnswer(response);
		
		// then
		assertThat(evaluation.isEmpty()).isTrue();
		verify(handler).publish(any(LogRecord.class));
		
	}

}
