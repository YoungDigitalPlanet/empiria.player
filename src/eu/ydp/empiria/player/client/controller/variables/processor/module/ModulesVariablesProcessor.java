package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.item.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

public class ModulesVariablesProcessor {

	private final ResponseChangesFinder responseChangesFinder;
	private final VariableProcessorFactory variableProcessorFactory;
	private final InitialProcessingResultFactory initialProcessingResultFactory;
	private final ModulesConstantVariablesInitializer constantVariablesInitializer;
	private ModulesProcessingResults processingResults;

	@Inject
	public ModulesVariablesProcessor(
			ResponseChangesFinder responseChangesFinder, 
			VariableProcessorFactory variableProcessorFactory, 
			InitialProcessingResultFactory initialProcessingResultFactory,
			ModulesConstantVariablesInitializer constantVariablesInitializer) {
		this.responseChangesFinder = responseChangesFinder;
		this.variableProcessorFactory = variableProcessorFactory;
		this.initialProcessingResultFactory = initialProcessingResultFactory;
		this.constantVariablesInitializer = constantVariablesInitializer;
	}

	public void initialize(Map<String, Response> responses){
		processingResults = constantVariablesInitializer.initializeTodoVariables(responses);
	}
	
	public ModulesProcessingResults processVariablesForResponses(Map<String, Response> responses, ProcessingMode processingMode) {
		List<DtoProcessedResponse> processedResponses = responseChangesFinder.findChangesOfAnswers(processingResults, responses);
		processVariablesForResponses(processedResponses, processingMode);
		return processingResults;
	}

	private void processVariablesForResponses(List<DtoProcessedResponse> changedResponses, ProcessingMode processingMode) {
		for (DtoProcessedResponse processedResponse : changedResponses) {
			if(processedResponse.containChanges())
				processChangedResponse(processedResponse, processingMode);
			else
				resetVariablesOfLastInteracktion(processedResponse);
		}
	}

	private void resetVariablesOfLastInteracktion(DtoProcessedResponse processedResponse) {
		DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
		UserInteractionVariables userInteractionVariables = processingResult.getUserInteractionVariables();
		userInteractionVariables.setLastAnswerChanges(new LastAnswersChanges());
		userInteractionVariables.setLastmistaken(false);
	}

	private void processChangedResponse(DtoProcessedResponse processedResponse, ProcessingMode processingMode) {
		Response response = processedResponse.getCurrentResponse();
		DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(response.cardinality, response.groups.size() > 0);
		
		UserInteractionVariables userInteractionVariables = processUserInteractionVariablesInCorrectMode(processedResponse, processingMode, variableProcessor);
		processingResult.setUserInteractionVariables(userInteractionVariables);
		
		GeneralVariables generalVariables = processGeneralVariables(response, variableProcessor);
		processingResult.setGeneralVariables(generalVariables);
		
		processingResults.setProcessingResultsForResponseId(response.getID(), processingResult);
	}

	private UserInteractionVariables processUserInteractionVariablesInCorrectMode(DtoProcessedResponse dtoChangedResponse, ProcessingMode processingMode,
			VariableProcessor variableProcessor) {
		UserInteractionVariables userInteractionVariables;
		if(processingMode == ProcessingMode.USER_INTERACT){
			userInteractionVariables = processUserInteractionVariables(variableProcessor, dtoChangedResponse);
		}else{
			userInteractionVariables = initialProcessingResultFactory.createInitialUserInteractionVariables();
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
