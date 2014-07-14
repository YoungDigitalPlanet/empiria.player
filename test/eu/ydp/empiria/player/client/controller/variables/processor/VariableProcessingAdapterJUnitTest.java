package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.common.collect.ImmutableMap;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessingAdapterJUnitTest {

	@InjectMocks
	private VariableProcessingAdapter testObj;

	@Mock
	private ModulesVariablesProcessor modulesVariablesProcessor;
	@Mock
	private GlobalVariablesProcessor globalVariablesProcessor;
	@Mock
	private ProcessingResultsToOutcomeMapConverterFacade resultsToOutcomeMapConverterFacade;
	@Mock
	private ModulesProcessingResults modulesProcessingResults;
	@Mock
	private AnswerEvaluationSupplier answerEvaluationProvider;
	@Mock
	private Map<String, Response> responses;
	@Mock
	private Map<String, Outcome> outcomes;

	@Test
	public void shouldProcessResponsesAndReturnResultConvertedToOldMap() throws Exception {
		// given
		ProcessingMode processingMode = ProcessingMode.USER_INTERACT;
		final String ID = "ID";

		when(modulesVariablesProcessor.processVariablesForResponses(responses, processingMode)).thenReturn(modulesProcessingResults);

		Map<String, DtoModuleProcessingResult> processingResults = ImmutableMap.of(ID, DtoModuleProcessingResult.fromDefaultVariables());
		when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(processingResults);

		GlobalVariables globalVariables = mock(GlobalVariables.class);
		when(globalVariablesProcessor.calculateGlobalVariables(processingResults, responses)).thenReturn(globalVariables);

		IUniqueModule sender = mock(IUniqueModule.class);

		// when
		testObj.processResponseVariables(responses, outcomes, processingMode, sender);

		// then
		verify(modulesVariablesProcessor).processVariablesForResponses(responses, processingMode);
		verify(modulesProcessingResults).getMapOfProcessingResults();
		verify(globalVariablesProcessor).calculateGlobalVariables(processingResults, responses);
		verify(resultsToOutcomeMapConverterFacade).convert(outcomes, modulesProcessingResults, globalVariables);
	}

	@Test
	public void shouldUpdateAnswerEvaluationProvider() throws Exception {
		// given
		ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

		when(modulesVariablesProcessor.processVariablesForResponses(responses, processingMode)).thenReturn(modulesProcessingResults);
		IUniqueModule sender = mock(IUniqueModule.class);

		// when
		testObj.processResponseVariables(responses, outcomes, processingMode, sender);

		// then
		verify(answerEvaluationProvider).updateModulesProcessingResults(modulesProcessingResults);
	}

}
