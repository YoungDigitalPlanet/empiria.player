package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

public class ResponseVariablesProcessor {

	private final VariableProcessorFactory variableProcessorFactory;
	
	@Inject
	public ResponseVariablesProcessor(VariableProcessorFactory variableProcessorFactory) {
		this.variableProcessorFactory = variableProcessorFactory;
	}

	public void resetLastUserInteractionVariables(UserInteractionVariables userInteractionVariables) {
		userInteractionVariables.setLastAnswerChanges(new LastAnswersChanges());
		userInteractionVariables.setLastmistaken(false);
	}

	public void processChangedResponse(DtoProcessedResponse processedResponse, ProcessingMode processingMode) {
		Response response = processedResponse.getCurrentResponse();
		DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
		
		boolean hasGroups = response.groups.size() > 0;
		boolean isInExpression = response.isInExpression();
		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(response.cardinality, hasGroups, isInExpression);
		
		UserInteractionVariables userInteractionVariables = processUserInteractionVariablesInCorrectMode(processedResponse, processingMode, variableProcessor);
		processingResult.setUserInteractionVariables(userInteractionVariables);
		
		GeneralVariables generalVariables = processGeneralVariables(response, variableProcessor);
		processingResult.setGeneralVariables(generalVariables);
	}

	private UserInteractionVariables processUserInteractionVariablesInCorrectMode(DtoProcessedResponse dtoChangedResponse, ProcessingMode processingMode,
			VariableProcessor variableProcessor) {
		UserInteractionVariables userInteractionVariables = dtoChangedResponse.getPreviousProcessingResult().getUserInteractionVariables();
		if(processingMode == ProcessingMode.USER_INTERACT){
			userInteractionVariables = processUserInteractionVariables(variableProcessor, dtoChangedResponse);
		}else{
			resetLastUserInteractionVariables(userInteractionVariables);
		}
		return userInteractionVariables;
	}
	
	private UserInteractionVariables processUserInteractionVariables(VariableProcessor variableProcessor, DtoProcessedResponse dtoChangedResponse) {
		Response currentResponse = dtoChangedResponse.getCurrentResponse();
		LastAnswersChanges answersChanges = dtoChangedResponse.getLastAnswersChanges();
		UserInteractionVariables previousUserInteractionVariables = dtoChangedResponse.getPreviousProcessingResult().getUserInteractionVariables();
		
		boolean lastmistaken = variableProcessor.checkLastmistaken(currentResponse, answersChanges);
		int mistakes = variableProcessor.calculateMistakes(lastmistaken, previousUserInteractionVariables.getMistakes());
		
		return new UserInteractionVariables(answersChanges, lastmistaken, mistakes);
	}
	
	private GeneralVariables processGeneralVariables(Response response, VariableProcessor variableProcessor) {
		int errors = variableProcessor.calculateErrors(response);
		int done = variableProcessor.calculateDone(response);
		List<Boolean> answersEvaluation = variableProcessor.evaluateAnswers(response);
		
		GeneralVariables generalVariables = new GeneralVariables(response.values, answersEvaluation, errors, done);
		return generalVariables;
	}
	
}
