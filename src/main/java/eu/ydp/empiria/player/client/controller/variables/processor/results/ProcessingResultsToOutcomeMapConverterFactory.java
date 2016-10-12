package eu.ydp.empiria.player.client.controller.variables.processor.results;

import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

public interface ProcessingResultsToOutcomeMapConverterFactory {

    ProcessingResultsToOutcomeMapConverter createConverter(ItemOutcomeStorageImpl outcomeManager);

}
