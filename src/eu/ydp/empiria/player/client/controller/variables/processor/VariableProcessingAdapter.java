package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverter;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariableProcessingAdapter {

	private static final Logger LOGGER = Logger.getLogger(VariableProcessingAdapter.class.getName());
	
	private final ModulesVariablesProcessor modulesVariablesProcessor;
	private final GlobalVariablesProcessor globalVariablesProcessor;
	private final ProcessingResultsToOutcomeMapConverterFactory processingResultsToOutcomeMapConverterFactory;
	private ModulesProcessingResults modulesProcessingResults;
	
	@Inject
	public VariableProcessingAdapter(
			@PageScoped ModulesVariablesProcessor modulesVariablesProcessor, 
			GlobalVariablesProcessor globalVariablesProcessor,
			ProcessingResultsToOutcomeMapConverterFactory processingResultsToOutcomeMapConverterFactory) {
		this.modulesVariablesProcessor = modulesVariablesProcessor;
		this.globalVariablesProcessor = globalVariablesProcessor;
		this.processingResultsToOutcomeMapConverterFactory = processingResultsToOutcomeMapConverterFactory;
	}

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode){
		modulesProcessingResults = modulesVariablesProcessor.processVariablesForResponses(responses, processingMode);
		
		List<DtoModuleProcessingResult> listOfProcessingResults = modulesProcessingResults.getListOfProcessingResults();
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(listOfProcessingResults);
		
		ProcessingResultsToOutcomeMapConverter resultsToOutcomeMapConverter = processingResultsToOutcomeMapConverterFactory.createConverter(outcomes);
		resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);
		resultsToOutcomeMapConverter.updateOutcomeMapWithGlobalVariables(globalVariables);
	}

	public List<Boolean> evaluateAnswer(Response response) {
		if(modulesProcessingResults == null){
			String message = "Cannot evaluate answers before first variables processing! Returning empty answerEvaluations list";
			LOGGER.warning(message);
			return Lists.newArrayList();
		}
		
		DtoModuleProcessingResult processingResult = modulesProcessingResults.getProcessingResultsForResponseId(response.getID());
		GeneralVariables generalVariables = processingResult.getGeneralVariables();
		List<Boolean> answersEvaluation = generalVariables.getAnswersEvaluation();
		return answersEvaluation;
	}
	
}
