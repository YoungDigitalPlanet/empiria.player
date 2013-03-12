package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.item.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariableProcessingAdapter {

	private ModulesVariablesProcessor moduleVariableProcessingManager;
	private GlobalVariablesProcessor globalVariablesProcessor;
	private ModulesProcessingResults modulesProcessingResults;
	
	@Inject
	public VariableProcessingAdapter(
			@PageScoped ModulesVariablesProcessor variableProcessingManager, 
			GlobalVariablesProcessor globalVariablesProcessor) {
		this.moduleVariableProcessingManager = variableProcessingManager;
		this.globalVariablesProcessor = globalVariablesProcessor;
	}

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode){
		modulesProcessingResults = moduleVariableProcessingManager.processVariablesForResponses(responses, processingMode);
		
		List<DtoModuleProcessingResult> listOfProcessingResults = modulesProcessingResults.getListOfProcessingResults();
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(listOfProcessingResults);
		
		ProcessingResultsToOutcomeMapConverter resultsToOutcomeMapConverter = new ProcessingResultsToOutcomeMapConverter(outcomes);
		resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);
		resultsToOutcomeMapConverter.updateOutcomeMapWithGlobalVariables(globalVariables);
	}

	public List<Boolean> evaluateAnswer(Response response) {
		if(modulesProcessingResults == null){
			throw new RuntimeException("Cannot evaluate answers before first variables processing!");
		}
		
		DtoModuleProcessingResult processingResult = modulesProcessingResults.getProcessingResultsForResponseId(response.getID());
		GeneralVariables generalVariables = processingResult.getGeneralVariables();
		List<Boolean> answersEvaluation = generalVariables.getAnswersEvaluation();
		return answersEvaluation;
	}
	
}
