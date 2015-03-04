package eu.ydp.empiria.player.client.controller.variables.processor.results;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

public class InitialProcessingResultFactory {

	public DtoModuleProcessingResult createProcessingResultWithInitialValues() {
		return DtoModuleProcessingResult.fromDefaultVariables();
	}

	public UserInteractionVariables createInitialUserInteractionVariables() {
		return new UserInteractionVariables();
	}
}
