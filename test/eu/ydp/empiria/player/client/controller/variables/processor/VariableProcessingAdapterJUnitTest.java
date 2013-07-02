package eu.ydp.empiria.player.client.controller.variables.processor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.thirdparty.guava.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessingAdapterJUnitTest {

	private VariableProcessingAdapter variableProcessingAdapter;

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

	@Before
	public void setUp() throws Exception {
		variableProcessingAdapter = new VariableProcessingAdapter(modulesVariablesProcessor, answerEvaluationProvider, globalVariablesProcessor, resultsToOutcomeMapConverterFacade);
	}

	@Test
	public void shouldProcessResponsesAndReturnResultConvertedToOldMap() throws Exception {
		// given
		Map<String, Response> responses = Maps.newHashMap();
		Map<String, Outcome> outcomes = Maps.newHashMap();
		ProcessingMode processingMode = ProcessingMode.USER_INTERACT;
		final String ID = "ID";

		when(modulesVariablesProcessor.processVariablesForResponses(responses, processingMode))
			.thenReturn(modulesProcessingResults);

		Map<String, DtoModuleProcessingResult> processingResults = ImmutableMap.of(ID, DtoModuleProcessingResult.fromDefaultVariables());
		when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(processingResults);

		GlobalVariables globalVariables = new GlobalVariables();
		when(globalVariablesProcessor.calculateGlobalVariables(processingResults, responses))
			.thenReturn(globalVariables);

		// when
		variableProcessingAdapter.processResponseVariables(responses, outcomes, processingMode);

		// then
		verify(modulesVariablesProcessor).processVariablesForResponses(responses, processingMode);
		verify(modulesProcessingResults).getMapOfProcessingResults();
		verify(globalVariablesProcessor).calculateGlobalVariables(processingResults, responses);
		verify(resultsToOutcomeMapConverterFacade).convert(outcomes, modulesProcessingResults, globalVariables);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldUpdateAnswerEvaluationProvider() throws Exception {
		// given
		when(modulesVariablesProcessor.processVariablesForResponses(anyMap(), any(ProcessingMode.class))).thenReturn(modulesProcessingResults);

		// when
		variableProcessingAdapter.processResponseVariables(mock(Map.class), mock(Map.class), ProcessingMode.USER_INTERACT);
		
		// then
		verify(answerEvaluationProvider).updateModulesProcessingResults(modulesProcessingResults);
	}

}
