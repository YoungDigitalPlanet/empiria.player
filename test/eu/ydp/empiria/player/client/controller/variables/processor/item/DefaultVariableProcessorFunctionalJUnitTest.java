package eu.ydp.empiria.player.client.controller.variables.processor.item;

import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTCHANGE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.MISTAKES;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.TODO;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingManagerAdapter;

public class DefaultVariableProcessorFunctionalJUnitTest {

	private Logger logger = Logger.getLogger(this.getClass().getName());
//	 private DefaultVariableProcessor defaultVariableProcessor;
	private VariableProcessingManagerAdapter defaultVariableProcessor;

	private OutcomeVariablesInitializer outcomeVariablesInitializer = new OutcomeVariablesInitializer();
	private ProcessingMode processingMode;

	@Before
	public void setUp() {
//		 defaultVariableProcessor = new DefaultVariableProcessor();

		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
			}
		});

		defaultVariableProcessor = injector.getInstance(VariableProcessingManagerAdapter.class);

		processingMode = ProcessingMode.USER_INTERACT;
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeMistakeAndUpdateVariables_singleCardinality() throws Exception {
		// given
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(response, "CorrectAnswer1", "CorrectAnswer2");
		setUpCurrentUserAnswers(response, "current not correct answer");

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, TODO, LASTMISTAKEN), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, TODO, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeCorrectAnswerAndUpdateVariables_singleCardinality() throws Exception {
		// given
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(response, "CorrectAnswer1", "CorrectAnswer2");
		setUpCurrentUserAnswers(response, "CorrectAnswer1", "CorrectAnswer2");

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeCorrectAnswerAndUpdateVariables_multipleCardinality() throws Exception {
		// given
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.MULTIPLE);
		setUpCorrectAnswers(response, "CorrectAnswer1", "CorrectAnswer2");
		setUpCurrentUserAnswers(response, "CorrectAnswer1", "CorrectAnswer2", "CorrectAnswer1");

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeMistakeAndUpdateVariables_multipleCardinality() throws Exception {
		// given
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.MULTIPLE);
		setUpCorrectAnswers(response, "CorrectAnswer1", "CorrectAnswer2");
		setUpCurrentUserAnswers(response, "current not correct answer", "CorrectAnswer1");

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, TODO, LASTMISTAKEN), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, TODO, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
	}

	/*
	 * 
	 * This test case is failing in old impl (it's a bug) and should beautifully
	 * pass in new impl There is a bug that cause incorrect calculation on
	 * MISTAKES, TODO, LASTMISTAKEN when we have grouped answers
	 */
	@Test
	public void testProcessResponseVariables_shouldRecognizeMistakeInSecondResponseAndUpdateVariables_commutativeAnswers() throws Exception {
		// given
		Response responseWithCorrectAnswerInGroup = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(responseWithCorrectAnswerInGroup, "CorrectAnswer assigned to first response");
		setUpCurrentUserAnswers(responseWithCorrectAnswerInGroup, "CorrectAnswer assigned to second response");
		setUpGroups(responseWithCorrectAnswerInGroup, "group1");

		Response responseWithWrongAnswer = prepareResponse("otherResponseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(responseWithWrongAnswer, "CorrectAnswer assigned to second response");
		setUpCurrentUserAnswers(responseWithWrongAnswer, "completly wrong answer");
		setUpGroups(responseWithWrongAnswer, "group1");

		Map<String, Response> responsesMap = convertToMap(responseWithCorrectAnswerInGroup, responseWithWrongAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("2"), Lists.newArrayList(TODO), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswerInGroup, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswerInGroup, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithWrongAnswer, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, TODO, MISTAKES, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithWrongAnswer, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeCorrectCommutativeAnswersAndUpdateVariables_commutativeAnswers() throws Exception {
		// given
		Response responseWithCorrectAnswer = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(responseWithCorrectAnswer, "CorrectAnswerFIRST");
		setUpCurrentUserAnswers(responseWithCorrectAnswer, "CorrectAnswerSECOND");
		setUpGroups(responseWithCorrectAnswer, "group1");

		Response anotherResponseWithCorrectAnswer = prepareResponse("otherResponseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(anotherResponseWithCorrectAnswer, "CorrectAnswerSECOND");
		setUpCurrentUserAnswers(anotherResponseWithCorrectAnswer, "CorrectAnswerFIRST");
		setUpGroups(anotherResponseWithCorrectAnswer, "group1");

		Map<String, Response> responsesMap = convertToMap(responseWithCorrectAnswer, anotherResponseWithCorrectAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("2"), Lists.newArrayList(DONE, TODO), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswer, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswer, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);

		assertResponseRelatedOutcomesHaveValue(anotherResponseWithCorrectAnswer, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(anotherResponseWithCorrectAnswer, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
	}

	/*
	 * We have two responses in one group, both of them have the same answer, so
	 * we can set only one as correct, and second as wrong
	 */
	@Test
	public void testProcessResponseVariables_shouldRecognizeAnswerAlreadyUsedInGroupAndUpdateVariables_commutativeAnswers() throws Exception {
		// given
		Response responseWithCorrectAnswerInGroup = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(responseWithCorrectAnswerInGroup, "CorrectAnswerFIRST");
		setUpCurrentUserAnswers(responseWithCorrectAnswerInGroup, "CorrectAnswerSECOND");
		setUpGroups(responseWithCorrectAnswerInGroup, "group1");

		Response responseWithAlreadyUsedAnswer = prepareResponse("otherResponseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(responseWithAlreadyUsedAnswer, "CorrectAnswerSECOND");
		setUpCurrentUserAnswers(responseWithAlreadyUsedAnswer, "CorrectAnswerSECOND");
		setUpGroups(responseWithAlreadyUsedAnswer, "group1");

		Map<String, Response> responsesMap = convertToMap(responseWithCorrectAnswerInGroup, responseWithAlreadyUsedAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		 assertGlobalOutcomesHaveValue(Lists.newArrayList("1"),
		 Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("2"), Lists.newArrayList(TODO), outcomes);

		 assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswerInGroup,
		 Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES,
		 LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswerInGroup, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithAlreadyUsedAnswer, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, TODO, LASTMISTAKEN, MISTAKES), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithAlreadyUsedAnswer, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldTreatEmptyAnswerAsNoAnswerAtAll() throws Exception {
		// given
		Response responseWithEmptyAnswer = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE);
		setUpCorrectAnswers(responseWithEmptyAnswer, "CorrectAnswer");
		setUpCurrentUserAnswers(responseWithEmptyAnswer, ""); // empty answer

		Map<String, Response> responsesMap = convertToMap(responseWithEmptyAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN, DONE), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("0"), Lists.newArrayList(DONE, MISTAKES, LASTMISTAKEN, ERRORS), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldTreatResponseWithMultipleCardinalityWithOnlyEmptyAnswersAsNoAnswerAtAll() throws Exception {
		// given
		Response responseWithEmptyAnswer = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.MULTIPLE);
		setUpCorrectAnswers(responseWithEmptyAnswer, "CorrectAnswer");
		setUpCurrentUserAnswers(responseWithEmptyAnswer, "", "", "");

		Map<String, Response> responsesMap = convertToMap(responseWithEmptyAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN, DONE), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("0"), Lists.newArrayList(DONE, MISTAKES, LASTMISTAKEN, ERRORS), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeWrongOrderOfAnswers_cardinalityOrdered() throws Exception {
		// given
		Response responseWithEmptyAnswer = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.ORDERED);
		setUpCorrectAnswers(responseWithEmptyAnswer, "firstAnswer", "secondAnswer");
		setUpCurrentUserAnswers(responseWithEmptyAnswer, "secondAnswer", "firstAnswer"); // user
																							// gave
																							// answer
																							// in
																							// not
																							// correct
																							// order

		Map<String, Response> responsesMap = convertToMap(responseWithEmptyAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		 assertGlobalOutcomesHaveValue(Lists.newArrayList("1"),
		 Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN, TODO), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("1"), Lists.newArrayList(TODO, MISTAKES, LASTMISTAKEN, ERRORS), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeCorrectOrderOfAnswers_cardinalityOrdered() throws Exception {
		// given
		Response responseWithEmptyAnswer = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.ORDERED);
		setUpCorrectAnswers(responseWithEmptyAnswer, "firstAnswer", "secondAnswer");
		setUpCurrentUserAnswers(responseWithEmptyAnswer, "firstAnswer", "secondAnswer"); // user
																							// gave
																							// answer
																							// in
																							// correct
																							// order

		Map<String, Response> responsesMap = convertToMap(responseWithEmptyAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);

		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("0"), Lists.newArrayList(MISTAKES, LASTMISTAKEN, ERRORS), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldCorrectlyCountTodo_InCorrectAnswerCountMode() throws Exception {
		// given
		Response responseWithCorrectAnswersCountMode = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.MULTIPLE);
		responseWithCorrectAnswersCountMode.setCountMode(CountMode.CORRECT_ANSWERS);
		setUpCorrectAnswers(responseWithCorrectAnswersCountMode, "firstAnswer", "secondAnswer", "thirdAnswer");

		Map<String, Response> responsesMap = convertToMap(responseWithCorrectAnswersCountMode);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		Integer expectedTODOCount = responseWithCorrectAnswersCountMode.correctAnswers.getResponseValuesCount();
		String expectedTODOVariableValue = expectedTODOCount.toString();
		assertGlobalOutcomesHaveValue(Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithCorrectAnswersCountMode, Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldCorrectlyCountTodo_InSingleCountMode() throws Exception {
		// given
		Response responseWithSingleCountMode = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.MULTIPLE);
		responseWithSingleCountMode.setCountMode(CountMode.SINGLE);
		setUpCorrectAnswers(responseWithSingleCountMode, "firstAnswer", "secondAnswer", "thirdAnswer");

		Map<String, Response> responsesMap = convertToMap(responseWithSingleCountMode);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
		ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		String expectedTODOVariableValue = "1";
		assertGlobalOutcomesHaveValue(Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(responseWithSingleCountMode, Lists.newArrayList(expectedTODOVariableValue), Lists.newArrayList(TODO), outcomes);
	}

	@Ignore
	@Test
	public void testProcessResponseVariables_shouldCorrectlyEvaluateAnswers_EvaluateUserMode() throws Exception {
		// given
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.MULTIPLE, Evaluate.USER);
		setUpCorrectAnswers(response, "correct1", "correct2", "correct3");
		setUpCurrentUserAnswers(response, "correct2", // this user answer is
														// correct
				"wrongAnswer", // this is wrong -> not existing in correct
								// answers list
				"correct1"); // this is correct
		List<Boolean> expectedAnswersEvaluation = Lists.newArrayList(true, false, true); // true
																							// if
																							// answer
																							// is
																							// correct

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
		List<Boolean> evaluatedAnswer = defaultVariableProcessor.evaluateAnswer(response);

		// then
		assertEquals(expectedAnswersEvaluation, evaluatedAnswer);
	}

	@Ignore
	@Test
	public void testProcessResponseVariables_shouldCorrectlyEvaluateAnswers_EvaluateCorrectMode() throws Exception {
		// given
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.MULTIPLE, Evaluate.CORRECT);
		setUpCurrentUserAnswers(response, "wrongAnswer", "correct3", "correct3");
		setUpCorrectAnswers(response, "correct1", // no user answer like this -
													// false evaluation
				"correct2", // no user answer like this - false evaluation
				"correct3"); // there is user answer equals this - true
								// evaluation
		List<Boolean> expectedAnswersEvaluation = Lists.newArrayList(false, false, true);

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
		ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
		List<Boolean> evaluatedAnswer = defaultVariableProcessor.evaluateAnswer(response);

		// then
		assertEquals(expectedAnswersEvaluation, evaluatedAnswer);
	}

	@Test
	public void testProcessResponseVariables_shouldCorrectlyUpdateVariablesWhenCallIsNotAfterUserInteraction() throws Exception {
		// given
		Response correctResponse = prepareResponse("correctResponseIdentifier", BaseType.STRING, Cardinality.MULTIPLE, Evaluate.USER);
		setUpCorrectAnswers(correctResponse, "correct1", "correct2", "correct3");
		setUpCurrentUserAnswers(correctResponse, "correct3", "correct3", "correct3");

		Response wrongResponse = prepareResponse("wrongResponseIdentifier", BaseType.STRING, Cardinality.MULTIPLE, Evaluate.USER);
		setUpCorrectAnswers(wrongResponse, "correct1", "correct2", "correct3");
		setUpCurrentUserAnswers(wrongResponse, "wrong", "wrong", "wrong");

		Map<String, Response> responsesMap = convertToMap(correctResponse, wrongResponse);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
		ProcessingMode processingMode = ProcessingMode.NOT_USER_INTERACT;

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("2"), Lists.newArrayList(TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE, ERRORS), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(MISTAKES, LASTMISTAKEN), outcomes);

		assertResponseRelatedOutcomesHaveValue(correctResponse, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(correctResponse, Lists.newArrayList("0"), Lists.newArrayList(MISTAKES, LASTMISTAKEN, ERRORS), outcomes);

		assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("1"), Lists.newArrayList(TODO, ERRORS), outcomes);
		assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("0"), Lists.newArrayList(MISTAKES, LASTMISTAKEN, DONE), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldHoldStateOfPreviousAnswers() throws Exception {
		Response wrongResponse = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE, Evaluate.USER);
		setUpCorrectAnswers(wrongResponse, "correct1");
		setUpCurrentUserAnswers(wrongResponse, "wrong");

		Map<String, Response> responsesMap = convertToMap(wrongResponse);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// assert outcomes after first call to processResponses
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(TODO, ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("1"), Lists.newArrayList(TODO, MISTAKES, LASTMISTAKEN, ERRORS), outcomes);

		// second call to processResponses
		Map<String, Outcome> outcomesAfterFirstCall = copyOutcomesMap(outcomes);
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		assertOutcomesMapsAreEqual(outcomesAfterFirstCall, outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldRecognizeLastchangeOfAnswer() throws Exception {
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE, Evaluate.USER);
		setUpCorrectAnswers(response, "correct");
		setUpCurrentUserAnswers(response, "wrong");

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// answer selected first time
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("+wrong"), Lists.newArrayList(LASTCHANGE), outcomes);

		// answer not changed
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
		assertResponseRelatedOutcomesHaveValue(response, new ArrayList<String>(), Lists.newArrayList(LASTCHANGE), outcomes);

		// answer removed
		response.values = Lists.newArrayList();
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("-wrong"), Lists.newArrayList(LASTCHANGE), outcomes);
	}

	@Test
	public void testProcessResponseVariables_shouldUpdateVariablesAndResetPreviousState() throws Exception {
		// given
		Response response = prepareResponse("responseIdentifier", BaseType.STRING, Cardinality.SINGLE, Evaluate.USER);
		setUpCorrectAnswers(response, "correct");
		setUpCurrentUserAnswers(response, "correct");

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
		ProcessingMode processingMode = ProcessingMode.RESET;

		// when
		// first time to generate state
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, ProcessingMode.USER_INTERACT);

		// second time to verify state was reseted
		setUpCurrentUserAnswers(response, "wrong");
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(LASTMISTAKEN, DONE, MISTAKES), outcomes);

		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(LASTMISTAKEN, DONE, MISTAKES), outcomes);
	}

	private void assertOutcomesMapsAreEqual(Map<String, Outcome> expectedOutcomes, Map<String, Outcome> actualOutcomes) {
		assertEquals(expectedOutcomes.size(), actualOutcomes.size());

		for (String key : expectedOutcomes.keySet()) {
			Outcome expectedOutcome = expectedOutcomes.get(key);
			Outcome actualOutcome = actualOutcomes.get(key);
			assertOuctomesAreEquals(expectedOutcome, actualOutcome);
		}
	}

	private void assertOuctomesAreEquals(Outcome expectedOutcome, Outcome actualOutcome) {
		assertEquals(expectedOutcome.identifier, actualOutcome.identifier);
		assertEquals(expectedOutcome.values, actualOutcome.values);
	}

	private Map<String, Outcome> copyOutcomesMap(Map<String, Outcome> outcomes) {
		Map<String, Outcome> copyOfMap = new HashMap<String, Outcome>();

		for (String key : outcomes.keySet()) {
			Outcome currentOutcome = outcomes.get(key);
			Outcome copyOfOutcome = copyOutcome(currentOutcome);
			copyOfMap.put(key, copyOfOutcome);
		}

		return copyOfMap;
	}

	private Outcome copyOutcome(Outcome currentOutcome) {
		Outcome copyOfOutcome = new Outcome();
		copyOfOutcome.values = new ArrayList<String>(currentOutcome.values);
		copyOfOutcome.identifier = currentOutcome.identifier;
		return copyOfOutcome;
	}

	private List<String> buildIdenfitiers(String prefix, Iterable<String> variables) {
		List<String> identifiers = Lists.newArrayList();
		for (String variable : variables) {
			String currentIdentifier = prefix + "-" + variable;
			identifiers.add(currentIdentifier);
		}
		return identifiers;
	}

	private void assertResponseRelatedOutcomesHaveValue(Response response, List<String> expectedValues, List<String> responseIdentifiers, Map<String, Outcome> outcomes) {
		assertOutcomesHaveValue(expectedValues, buildIdenfitiers(response.getID(), responseIdentifiers), outcomes);
	}

	private void assertGlobalOutcomesHaveValue(List<String> expectedValues, List<String> responseIdentifiers, Map<String, Outcome> outcomes) {
		assertOutcomesHaveValue(expectedValues, responseIdentifiers, outcomes);
	}

	private void assertOutcomesHaveValue(List<String> expectedValues, List<String> responseIdentifiers, Map<String, Outcome> outcomes) {
		for (String responseIdentifier : responseIdentifiers) {
			Outcome outcome = outcomes.get(responseIdentifier);
			logger.info("Asserting variable with identifier: " + responseIdentifier);
			assertOutcomeValue(outcome, expectedValues);
		}
	}

	private void assertOutcomeValue(Outcome outcome, List<String> expectedValues) {
		List<String> currentOutcomeValues = outcome.values;
		assertEquals(expectedValues, currentOutcomeValues);
	}

	private Map<String, Outcome> prepareInitialOutcomes(Map<String, Response> responsesMap) {
		Map<String, Outcome> outcomes = Maps.newHashMap();
		outcomeVariablesInitializer.initializeOutcomeVariables(responsesMap, outcomes);
		return outcomes;
	}

	private Map<String, Response> convertToMap(Response... responses) {
		Map<String, Response> responsesMap = Maps.newHashMap();
		for (Response response : responses) {
			if (responsesMap.containsKey(response.getID())) {
				throw new RuntimeException("Response with id: " + response.getID() + " already exists in map!");
			}
			responsesMap.put(response.getID(), response);
		}
		return responsesMap;
	}

	private void setUpGroups(Response response, String... groupsNames) {
		response.groups = Arrays.asList(groupsNames);
	}

	private void setUpCurrentUserAnswers(Response response, String... currentAnswers) {
		response.values = Arrays.asList(currentAnswers);
	}

	private void setUpCorrectAnswers(Response response, String... correctAnswers) {
		CorrectAnswers correctAnswersHolder = new CorrectAnswers();
		for (String correctAnswer : correctAnswers) {
			ResponseValue responseValue = new ResponseValue(correctAnswer);
			correctAnswersHolder.add(responseValue);
		}
		response.correctAnswers = correctAnswersHolder;
	}

	private Response prepareResponse(String identifier, BaseType baseType, Cardinality cardinality) {
		Evaluate evaluate = Evaluate.USER;
		return prepareResponse(identifier, baseType, cardinality, evaluate);
	}

	private Response prepareResponse(String identifier, BaseType baseType, Cardinality cardinality, Evaluate evaluateType) {
		CorrectAnswers correctAnswers = new CorrectAnswers();
		List<String> values = Lists.newArrayList();
		List<String> groups = Lists.newArrayList();
		Response response = new Response(correctAnswers, values, groups, identifier, evaluateType, baseType, cardinality);
		return response;
	}

}
