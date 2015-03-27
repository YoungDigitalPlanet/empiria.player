package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;

public class ProcessingResultsToOutcomeMapConverterFacade {

	@Inject
	private ProcessingResultsToOutcomeMapConverterFactory processingResultsToOutcomeMapConverterFactory;

	public void convert(Map<String, Outcome> outcomes, ModulesProcessingResults modulesProcessingResults, GlobalVariables globalVariables) {
		ProcessingResultsToOutcomeMapConverter resultsToOutcomeMapConverter = processingResultsToOutcomeMapConverterFactory.createConverter(outcomes);
		resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);
		resultsToOutcomeMapConverter.updateOutcomeMapWithGlobalVariables(globalVariables);
	}
}
