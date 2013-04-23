package eu.ydp.empiria.player.client.controller.variables.processor;

import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.OutcomeController;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class MistakesInitializer {

	private final ModulesProcessingResults processingResults;
	private final OutcomeController outcomeController;

	@Inject
	public MistakesInitializer(@PageScoped ModulesProcessingResults processingResults, OutcomeController outcomeController) {
		this.processingResults = processingResults;
		this.outcomeController = outcomeController;
	}

	public void initialize(Map<String, Outcome> outcomes) {
		Map<String, Integer> mistakeResponses = outcomeController.getAllMistakes(outcomes);
		updateMistakes(mistakeResponses);
	}

	private void updateMistakes(Map<String, Integer> mistakeResponses) {
		for (String key : mistakeResponses.keySet()) {
			Integer mistakesNumber = mistakeResponses.get(key);

			DtoModuleProcessingResult moduleProcessingResult = processingResults.getProcessingResultsForResponseId(key);
			UserInteractionVariables userInteractionVariables = moduleProcessingResult.getUserInteractionVariables();
			userInteractionVariables.setMistakes(mistakesNumber);
		}
	}
}
