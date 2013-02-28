package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.item.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;

public class VariableProcessingManagerAdapter {

	private ModulesVariablesProcessor moduleVariableProcessingManager;
	private GlobalVariablesProcessor globalVariablesProcessor;
	private boolean isModuleVariableProcessorInitialized = false;
	
	@Inject
	public VariableProcessingManagerAdapter(ModulesVariablesProcessor variableProcessingManager, GlobalVariablesProcessor globalVariablesProcessor) {
		this.moduleVariableProcessingManager = variableProcessingManager;
		this.globalVariablesProcessor = globalVariablesProcessor;
	}

	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, ProcessingMode processingMode){
		if(!isModuleVariableProcessorInitialized){
			moduleVariableProcessingManager.initialize(responses);
			isModuleVariableProcessorInitialized = true;
		}
		
		ModulesProcessingResults modulesProcessingResults = moduleVariableProcessingManager.processVariablesForResponses(responses, processingMode);
		
		List<DtoModuleProcessingResult> listOfProcessingResults = modulesProcessingResults.getListOfProcessingResults();
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(listOfProcessingResults);
		
		ProcessingResultsToOutcomeMapConverter resultsToOutcomeMapConverter = new ProcessingResultsToOutcomeMapConverter(outcomes);
		resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);
		resultsToOutcomeMapConverter.updateOutcomeMapWithGlobalVariables(globalVariables);
	}
	
}
