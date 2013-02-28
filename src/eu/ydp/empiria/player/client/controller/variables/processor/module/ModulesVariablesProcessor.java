package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoChangedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.item.ChangedResponsesFinder;
import eu.ydp.empiria.player.client.controller.variables.processor.item.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.UserInteractionVariables;

public class ModulesVariablesProcessor {

	private final ChangedResponsesFinder changedResponsesFinder;
	private final VariableProcessorFactory variableProcessorFactory;
	private final InitialProcessingResultFactory initialProcessingResultFactory;
	private final ModulesConstantVariablesInitializer constantVariablesInitializer;
	private ModulesProcessingResults processingResults;

	@Inject
	public ModulesVariablesProcessor(
			ChangedResponsesFinder changedResponsesFinder, 
			VariableProcessorFactory variableProcessorFactory, 
			InitialProcessingResultFactory initialProcessingResultFactory,
			ModulesConstantVariablesInitializer constantVariablesInitializer) {
		this.changedResponsesFinder = changedResponsesFinder;
		this.variableProcessorFactory = variableProcessorFactory;
		this.initialProcessingResultFactory = initialProcessingResultFactory;
		this.constantVariablesInitializer = constantVariablesInitializer;
	}

	public void initialize(Map<String, Response> responses){
		processingResults = constantVariablesInitializer.initializeTodoVariables(responses);
	}
	
	public ModulesProcessingResults processVariablesForResponses(Map<String, Response> responses, ProcessingMode processingMode) {
		List<DtoChangedResponse> changedResponses = changedResponsesFinder.findResponsesWhereAnswersChanged(processingResults, responses);
		processVariablesForChangedResponses(changedResponses, processingMode);
		return processingResults;
	}

	private void processVariablesForChangedResponses(List<DtoChangedResponse> changedResponses, ProcessingMode processingMode) {
		for (DtoChangedResponse dtoChangedResponse : changedResponses) {
			processChangedResponse(dtoChangedResponse, processingMode);
		}
	}

	private void processChangedResponse(DtoChangedResponse dtoChangedResponse, ProcessingMode processingMode) {
		Response response = dtoChangedResponse.getCurrentResponse();
		DtoModuleProcessingResult processingResult = dtoChangedResponse.getPreviousProcessingResult();
		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(response.cardinality, response.groups.size() > 0);
		
		UserInteractionVariables userInteractionVariables = processUserInteractionVariablesInCorrectMode(dtoChangedResponse, processingMode, variableProcessor);
		processingResult.setUserInteractionVariables(userInteractionVariables);
		
		GeneralVariables generalVariables = processGeneralVariables(response, variableProcessor);
		processingResult.setGeneralVariables(generalVariables);
		
		processingResults.setProcessingResultsForResponseId(response.getID(), processingResult);
	}

	private UserInteractionVariables processUserInteractionVariablesInCorrectMode(DtoChangedResponse dtoChangedResponse, ProcessingMode processingMode,
			VariableProcessor variableProcessor) {
		UserInteractionVariables userInteractionVariables;
		if(processingMode == ProcessingMode.USER_INTERACT){
			userInteractionVariables = processUserInteractionVariables(variableProcessor, dtoChangedResponse);
		}else{
			userInteractionVariables = initialProcessingResultFactory.createInitialUserInteractionVariables();
		}
		return userInteractionVariables;
	}

	private UserInteractionVariables processUserInteractionVariables(VariableProcessor variableProcessor, DtoChangedResponse dtoChangedResponse) {
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
		
		GeneralVariables generalVariables = new GeneralVariables(response.values, errors, done);
		return generalVariables;
	}

}
