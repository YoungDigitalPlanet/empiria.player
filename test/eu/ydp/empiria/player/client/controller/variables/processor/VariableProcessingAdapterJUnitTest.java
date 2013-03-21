package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;

import static org.junit.Assert.*;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.global.DtoModuleProcessingResultBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverter;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessingAdapterJUnitTest {

	private VariableProcessingAdapter variableProcessingAdapter;
	private ReflectionsUtils reflectionsUtils = new ReflectionsUtils();

	@Mock
	private ModulesVariablesProcessor modulesVariablesProcessor;
	@Mock
	private GlobalVariablesProcessor globalVariablesProcessor;
	@Mock
	private ProcessingResultsToOutcomeMapConverterFactory processingResultsToOutcomeMapConverterFactory;
	@Mock
	private ModulesProcessingResults modulesProcessingResults;

	@Before
	public void setUp() throws Exception {
		variableProcessingAdapter = new VariableProcessingAdapter(modulesVariablesProcessor, globalVariablesProcessor, processingResultsToOutcomeMapConverterFactory);
	}

	@Test
	public void shouldProcessResponsesAndReturnResultConvertedToOldMap() throws Exception {
		Map<String, Response> responses = Maps.newHashMap();
		Map<String, Outcome> outcomes = Maps.newHashMap();
		ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

		when(modulesVariablesProcessor.processVariablesForResponses(responses, processingMode))
			.thenReturn(modulesProcessingResults);

		List<DtoModuleProcessingResult> collectionOfProcessingResults = Lists.newArrayList(DtoModuleProcessingResult.fromDefaultVariables());
		when(modulesProcessingResults.getListOfProcessingResults())
			.thenReturn(collectionOfProcessingResults);

		GlobalVariables globalVariables = new GlobalVariables();
		when(globalVariablesProcessor.calculateGlobalVariables(collectionOfProcessingResults))
			.thenReturn(globalVariables);

		ProcessingResultsToOutcomeMapConverter resultsToOutcomeMapConverter = Mockito.mock(ProcessingResultsToOutcomeMapConverter.class);
		when(processingResultsToOutcomeMapConverterFactory.createConverter(outcomes)).thenReturn(resultsToOutcomeMapConverter);

		variableProcessingAdapter.processResponseVariables(responses, outcomes, processingMode);

		verify(modulesVariablesProcessor).processVariablesForResponses(responses, processingMode);
		verify(modulesProcessingResults).getListOfProcessingResults();
		verify(globalVariablesProcessor).calculateGlobalVariables(collectionOfProcessingResults);
		verify(processingResultsToOutcomeMapConverterFactory).createConverter(outcomes);
		verify(resultsToOutcomeMapConverter).updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);
		verify(resultsToOutcomeMapConverter).updateOutcomeMapWithGlobalVariables(globalVariables);
	}

	@Test
	public void shouldReturnCachedAnswerEvaluations() throws Exception {
		Response response = new ResponseBuilder().withIdentifier("respIde")
				.build();
		List<Boolean> expectedEvaluations = Lists.newArrayList(true, false);
		DtoModuleProcessingResult processingResults = new DtoModuleProcessingResultBuilder().withAnswerEvaluations(expectedEvaluations)
				.build();


		setUpCachedEvaluations();

		when(modulesProcessingResults.getProcessingResultsForResponseId(response.identifier))
			.thenReturn(processingResults);

		List<Boolean> evaluations = variableProcessingAdapter.evaluateAnswer(response);
		
		assertEquals(expectedEvaluations, evaluations);
	}
	
	@Test
	public void shouldReturnEmptyEvaluationsWhenNoResultsProcessingWasPerformedYet() throws Exception {
		Response response = new ResponseBuilder().withIdentifier("respIde")
				.build();
		
		List<Boolean> evaluations = variableProcessingAdapter.evaluateAnswer(response);
		
		List<Boolean> expectedEvaluations = Lists.newArrayList();
		assertEquals(expectedEvaluations, evaluations);
	}

	private void setUpCachedEvaluations() throws NoSuchFieldException, IllegalAccessException {
		reflectionsUtils.setValueInObjectOnField("modulesProcessingResults", variableProcessingAdapter, modulesProcessingResults);
	}

}
