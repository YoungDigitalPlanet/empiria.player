package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariableProcessingAdapter {

	private final ModulesVariablesProcessor modulesVariablesProcessor;
	private final GlobalVariablesProcessor globalVariablesProcessor;
	private final AnswerEvaluationSupplier answerEvaluationProvider;
	private final ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade;

	@Inject
	public VariableProcessingAdapter(@PageScoped ModulesVariablesProcessor modulesVariablesProcessor,
			@PageScoped AnswerEvaluationSupplier answerEvaluationProvider, GlobalVariablesProcessor globalVariablesProcessor,
			ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade) {
		this.modulesVariablesProcessor = modulesVariablesProcessor;
		this.globalVariablesProcessor = globalVariablesProcessor;
		this.mapConverterFacade = mapConverterFacade;
		this.answerEvaluationProvider = answerEvaluationProvider;
	}

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode) {
		ModulesProcessingResults modulesProcessingResults = modulesVariablesProcessor.processVariablesForResponses(responses, processingMode);

		Map<String, DtoModuleProcessingResult> mapOfProcessingResults = modulesProcessingResults.getMapOfProcessingResults();

		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responses);

		mapConverterFacade.convert(outcomes, modulesProcessingResults, globalVariables);

		answerEvaluationProvider.updateModulesProcessingResults(modulesProcessingResults);
	}
}
