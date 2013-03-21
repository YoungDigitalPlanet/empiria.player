package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.Map;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

public interface ProcessingResultsToOutcomeMapConverterFactory {

	ProcessingResultsToOutcomeMapConverter createConverter(Map<String, Outcome> outcomes);
	
}
