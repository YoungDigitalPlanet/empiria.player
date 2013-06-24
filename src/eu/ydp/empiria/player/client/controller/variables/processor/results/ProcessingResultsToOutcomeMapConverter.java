package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ConstantVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

public class ProcessingResultsToOutcomeMapConverter {

	private static final String TODO = "TODO";
	private static final String DONE = "DONE";
	private static final String ERRORS = "ERRORS";
	private static final String MISTAKES = "MISTAKES";
	private static final String LASTMISTAKEN = "LASTMISTAKEN";
	private static final String LASTCHANGE = "LASTCHANGE";

	private final Map<String, Outcome> outcomes;
	private final AnswersChangesFormater answersChangesFormater;

	@Inject
	public ProcessingResultsToOutcomeMapConverter(@Assisted Map<String, Outcome> outcomes, AnswersChangesFormater answersChangesFormater) {
		this.outcomes = outcomes;
		this.answersChangesFormater = answersChangesFormater;
	}

	public void updateOutcomeMapWithGlobalVariables(GlobalVariables globalVariables) {
		insertVariable(TODO, globalVariables.getTodo());
		insertVariable(DONE, globalVariables.getDone());
		insertVariable(ERRORS, globalVariables.getErrors());
		insertVariable(MISTAKES, globalVariables.getMistakes());
		insertVariable(LASTMISTAKEN, globalVariables.isLastMistaken());
	}
	
	public void updateOutcomeMapByModulesProcessingResults(ModulesProcessingResults modulesProcessingResults) {
		Set<String> idsOfProcessedResponses = modulesProcessingResults.getIdsOfProcessedResponses();
		for (String responseId : idsOfProcessedResponses) {
			DtoModuleProcessingResult moduleProcessingResult = modulesProcessingResults.getProcessingResultsForResponseId(responseId);
			insertVariablesToMap(responseId, moduleProcessingResult);
		}
	}

	private void insertVariablesToMap(String responseId, DtoModuleProcessingResult moduleProcessingResult) {
		insertConstantVariables(responseId, moduleProcessingResult.getConstantVariables());
		insertUserInteractionVariables(responseId, moduleProcessingResult.getUserInteractionVariables());
		insertGeneralVariables(responseId, moduleProcessingResult.getGeneralVariables());
	}

	private void insertConstantVariables(String responseId, ConstantVariables constantVariables) {
		int todoValue = constantVariables.getTodo();
		insertModuleVariable(TODO, responseId, todoValue);
	}

	private void insertUserInteractionVariables(String responseId, UserInteractionVariables userInteractionVariables) {
		int mistakesValue = userInteractionVariables.getMistakes();
		insertModuleVariable(MISTAKES, responseId, mistakesValue);
		
		boolean lastMistakenValue = userInteractionVariables.isLastmistaken();
		insertModuleVariable(LASTMISTAKEN, responseId, lastMistakenValue);
		
		LastAnswersChanges lastAnswerChanges = userInteractionVariables.getLastAnswerChanges();
		List<String> lastchanges = answersChangesFormater.formatLastAnswerChanges(lastAnswerChanges);
		String lastchangeIdentifier = buildModuleVariableIdentifier(LASTCHANGE, responseId);
		insertVariable(lastchangeIdentifier, lastchanges);
	}


	private void insertGeneralVariables(String responseId, GeneralVariables generalVariables) {
		int errorValue = generalVariables.getErrors();
		insertModuleVariable(ERRORS, responseId, errorValue);
		
		int doneValue = generalVariables.getDone();
		insertModuleVariable(DONE, responseId, doneValue);
	}
	
	private void insertModuleVariable(String variableName, String moduleId, int value){
		insertModuleVariable(variableName, moduleId, String.valueOf(value));
	}

	private void insertModuleVariable(String variableName, String moduleId, boolean value){
		int valueConvertedToInt = convertBooleanToInt(value);
		insertModuleVariable(variableName, moduleId, valueConvertedToInt);
	}

	private int convertBooleanToInt(boolean value) {
		int valueConvertedToInt;
		if(value){
			valueConvertedToInt = 1;
		}else{
			valueConvertedToInt = 0;
		}
		return valueConvertedToInt;
	}

	private void insertModuleVariable(String variableName, String moduleId, String value){
		String variableIdentifier = buildModuleVariableIdentifier(variableName, moduleId);
		insertVariable(variableIdentifier, value);
	}

	private String buildModuleVariableIdentifier(String variableName, String moduleId) {
		String variableIdentifier = moduleId+"-"+variableName;
		return variableIdentifier;
	}

	private void insertVariable(String identifier, int value) {
		insertVariable(identifier, String.valueOf(value));
	}
	
	private void insertVariable(String identifier, boolean value) {
		int valueConvertedToInt = convertBooleanToInt(value);
		insertVariable(identifier, valueConvertedToInt);
	}
	
	private void insertVariable(String identifier, String value) {
		Outcome outcome = new Outcome(identifier, Cardinality.SINGLE, ""+value);
		outcomes.put(identifier, outcome);
	}
	
	private void insertVariable(String identifier, List<String> values) {
		Outcome outcome = new Outcome(identifier, Cardinality.MULTIPLE);
		outcome.values = values;
		outcomes.put(identifier, outcome);
	}
}
