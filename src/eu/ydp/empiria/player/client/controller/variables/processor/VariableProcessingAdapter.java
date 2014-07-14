package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.IUniqueModule;

import java.util.Map;

public class VariableProcessingAdapter {

	private final ModulesVariablesProcessor modulesVariablesProcessor;
	private final AnswerEvaluationSupplier answerEvaluationProvider;
	private final ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade;
	private final GlobalVariablesProvider globalVariablesProvider;

	@Inject
	public VariableProcessingAdapter(@PageScoped ModulesVariablesProcessor modulesVariablesProcessor,
			@PageScoped AnswerEvaluationSupplier answerEvaluationProvider, ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade,
			GlobalVariablesProvider globalVariablesProvider) {
		this.modulesVariablesProcessor = modulesVariablesProcessor;
		this.mapConverterFacade = mapConverterFacade;
		this.answerEvaluationProvider = answerEvaluationProvider;
		this.globalVariablesProvider = globalVariablesProvider;
	}

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode, IUniqueModule sender) {
		ModulesProcessingResults modulesProcessingResults = modulesVariablesProcessor.processVariablesForResponses(responses, processingMode);
		GlobalVariables globalVariables = globalVariablesProvider.retrieveGlobalVariables(modulesProcessingResults, responses, sender);
		mapConverterFacade.convert(outcomes, modulesProcessingResults, globalVariables);
		answerEvaluationProvider.updateModulesProcessingResults(modulesProcessingResults);
	}
}
