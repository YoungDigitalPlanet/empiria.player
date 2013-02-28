package eu.ydp.empiria.player.client.controller.variables.processor.results;

public class InitialProcessingResultFactory {

	public DtoModuleProcessingResult createProcessingResultWithInitialValues(){
		return DtoModuleProcessingResult.fromDefaultVariables();
	}
	
	public UserInteractionVariables createInitialUserInteractionVariables(){
		return new UserInteractionVariables();
	}
}
