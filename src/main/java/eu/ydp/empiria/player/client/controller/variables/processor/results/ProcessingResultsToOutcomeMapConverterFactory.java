package eu.ydp.empiria.player.client.controller.variables.processor.results;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

import java.util.Map;

public interface ProcessingResultsToOutcomeMapConverterFactory {

    ProcessingResultsToOutcomeMapConverter createConverter(Map<String, Outcome> outcomes);

}
