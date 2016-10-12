package eu.ydp.empiria.player.client.controller.variables.processor.results;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

public class ProcessingResultsToOutcomeMapConverterFacade {

    @Inject
    private ProcessingResultsToOutcomeMapConverterFactory processingResultsToOutcomeMapConverterFactory;

    public void convert(ItemOutcomeStorageImpl outcomeManager, ModulesProcessingResults modulesProcessingResults, GlobalVariables globalVariables) {
        ProcessingResultsToOutcomeMapConverter resultsToOutcomeMapConverter = processingResultsToOutcomeMapConverterFactory.createConverter(outcomeManager);
        resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);
        resultsToOutcomeMapConverter.updateOutcomeMapWithGlobalVariables(globalVariables);
    }
}
