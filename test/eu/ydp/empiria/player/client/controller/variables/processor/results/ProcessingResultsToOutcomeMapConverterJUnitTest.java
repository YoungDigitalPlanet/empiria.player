package eu.ydp.empiria.player.client.controller.variables.processor.results;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ConstantVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

public class ProcessingResultsToOutcomeMapConverterJUnitTest {

	private ProcessingResultsToOutcomeMapConverter resultsToOutcomeMapConverter;
	private Map<String, Outcome> outcomes;
	private ModulesProcessingResults modulesProcessingResults;
	private final String variableIdentifier = "variableID";
	private AnswersChangesFormater answerChangesFormatter;

	@Before
	public void setUp() {
		modulesProcessingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
		outcomes = Maps.newHashMap();

		answerChangesFormatter = new AnswersChangesFormater();
		resultsToOutcomeMapConverter = new ProcessingResultsToOutcomeMapConverter(outcomes, answerChangesFormatter);
	}

	@Test
	public void shouldConvertGlobalVariables() throws Exception {
		int todo = 13;
		int errors = 3123;
		int done = 23424;
		LastMistaken lastmistaken = LastMistaken.WRONG;
		int mistakes = 14234;
		GlobalVariables globalVariables = new GlobalVariables(todo, done, errors, mistakes, lastmistaken);

		resultsToOutcomeMapConverter.updateOutcomeMapWithGlobalVariables(globalVariables);

		assertGlobalVariableExists(TODO, todo);
		assertGlobalVariableExists(DONE, done);
		assertGlobalVariableExists(ERRORS, errors);
		assertGlobalVariableExists(MISTAKES, mistakes);
		assertGlobalVariableExists(LASTMISTAKEN, lastmistaken);
	}

	@Test
	public void shouldConvertGeneralModuleVariables() throws Exception {
		DtoModuleProcessingResult processingResult = createProcessingResult(variableIdentifier);

		resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);

		GeneralVariables generalVariables = processingResult.getGeneralVariables();
		assertModuleVariableExists(DONE, generalVariables.getDone());
		assertModuleVariableExists(ERRORS, generalVariables.getErrors());
	}

	@Test
	public void shouldConvertUserInteractionModuleVariables() throws Exception {
		DtoModuleProcessingResult processingResult = createProcessingResult(variableIdentifier);
		List<String> addedAnswers = Lists.newArrayList("addedAnswer");
		List<String> removedAnswers = Lists.newArrayList("removedAnswer");
		LastAnswersChanges lastAnswerChanges = new LastAnswersChanges(addedAnswers, removedAnswers);
		processingResult.getUserInteractionVariables().setLastAnswerChanges(lastAnswerChanges);

		resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);

		UserInteractionVariables userInteractionVariables = processingResult.getUserInteractionVariables();
		assertModuleVariableExists(LASTMISTAKEN, userInteractionVariables.getLastmistaken());
		assertModuleVariableExists(MISTAKES, userInteractionVariables.getMistakes());
		assertModuleVariableExistsWithValues(LASTCHANGE, Lists.newArrayList("+addedAnswer", "-removedAnswer"));
	}

	private void assertModuleVariableExistsWithValues(VariableName variableName, List<String> values) {
		String variableId = (variableIdentifier + "-" + variableName);
		Outcome outcome = outcomes.get(variableId);
		assertNotNull(outcome);
		assertEquals(values, outcome.values);
	}

	@Test
	public void shouldConvertConstantModuleVariables() throws Exception {
		DtoModuleProcessingResult processingResult = createProcessingResult(variableIdentifier);

		resultsToOutcomeMapConverter.updateOutcomeMapByModulesProcessingResults(modulesProcessingResults);

		ConstantVariables constantVariables = processingResult.getConstantVariables();
		assertModuleVariableExists(TODO, constantVariables.getTodo());
	}

	private void assertModuleVariableExists(VariableName variableName, int done) {
		assertVariableExists(variableIdentifier + "-" + variableName, "" + done);
	}

	private void assertModuleVariableExists(VariableName variableName, LastMistaken lastmistaken) {
		assertVariableExists(variableIdentifier + "-" + variableName, lastmistaken.toString());
	}

	private DtoModuleProcessingResult createProcessingResult(String id) {
		return modulesProcessingResults.getProcessingResultsForResponseId(id);
	}

	private void assertGlobalVariableExists(VariableName variableName, LastMistaken variableValue) {
		assertGlobalVariableExists(variableName, variableValue.toString());
	}

	private void assertGlobalVariableExists(VariableName variableName, int todo) {
		assertVariableExists(variableName.toString(), "" + todo);
	}

	private void assertGlobalVariableExists(VariableName variableName, String variableValue) {
		assertVariableExists(variableName.toString(), variableValue);
	}

	private void assertVariableExists(String variableId, String variableValue) {
		Outcome outcome = outcomes.get(variableId);
		assertNotNull(outcome);
		assertEquals(variableValue, outcome.values.get(0));
	}
}
