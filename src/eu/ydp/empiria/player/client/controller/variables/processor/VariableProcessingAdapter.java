package eu.ydp.empiria.player.client.controller.variables.processor;

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
import eu.ydp.empiria.player.client.module.IIgnored;
import eu.ydp.empiria.player.client.module.IUniqueModule;

import java.util.Map;

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

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode, IUniqueModule sender) {
		ModulesProcessingResults modulesProcessingResults = modulesVariablesProcessor.processVariablesForResponses(responses, processingMode);
		GlobalVariables globalVariables = getGlobalVariables(responses, modulesProcessingResults, sender);
		mapConverterFacade.convert(outcomes, modulesProcessingResults, globalVariables);
		answerEvaluationProvider.updateModulesProcessingResults(modulesProcessingResults);
	}

	private GlobalVariables getGlobalVariables(Map<String, Response> responses, ModulesProcessingResults modulesProcessingResults, IUniqueModule sender) {
		if (sender instanceof IIgnored) {
			if (((IIgnored) sender).isIgnored()) {
				return GlobalVariables.createEmpty();
			}
		}
		return getGlobalVariablesFromResponses(responses, modulesProcessingResults);
	}

	private GlobalVariables getGlobalVariablesFromResponses(Map<String, Response> responses, ModulesProcessingResults modulesProcessingResults) {
		Map<String, DtoModuleProcessingResult> mapOfProcessingResults = modulesProcessingResults.getMapOfProcessingResults();
		return globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responses);
	}
}
