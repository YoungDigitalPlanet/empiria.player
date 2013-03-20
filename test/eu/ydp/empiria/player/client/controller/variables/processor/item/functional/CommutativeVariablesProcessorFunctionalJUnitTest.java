package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;

import java.util.Map;

import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;

public class CommutativeVariablesProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase {

	@Test
	public void shouldRecognizeMistakeInSecondResponse() throws Exception {
		// given
		Response responseWithCorrectAnswerInGroup = builder()
				.withCorrectAnswers("CorrectAnswer assigned to first response")
				.withCurrentUserAnswers("CorrectAnswer assigned to second response")
				.build();

		Response responseWithWrongAnswer = builder()
				.withCorrectAnswers("CorrectAnswer assigned to second response")
				.withCurrentUserAnswers("completly wrong answer")
				.withIdentifier("responseWithWrongAnswerId")
				.build();

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
	public void shouldRecognizeCorrectAnswerChangedToAnotherCorrectAnswer() throws Exception {
		// given
		Response correctResponse = builder().withCorrectAnswers("CorrectAnswer1").withCurrentUserAnswers("CorrectAnswer2").build();

		Response wrongResponse = builder()
				.withIdentifier("wrongResponseId")
				.withCorrectAnswers("CorrectAnswer2")
				.withCurrentUserAnswers("completly wrong answer")
				.build();

		Map<String, Response> responsesMap = convertToMap(correctResponse, wrongResponse);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		// first call to generate processor state
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// change user answers to other correct answer
		setUpCurrentUserAnswers(correctResponse, "CorrectAnswer1");

		// second call to verify correct behavior
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, DONE), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(LASTMISTAKEN), outcomes);

		assertResponseRelatedOutcomesHaveValue(correctResponse, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(correctResponse, Lists.newArrayList("1"), Lists.newArrayList(DONE), outcomes);

		assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("1"), Lists.newArrayList(ERRORS), outcomes);
		assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("0"), Lists.newArrayList(DONE, LASTMISTAKEN), outcomes);
	}

	@Test
	public void shouldRecognizeWrongAnswerChangedToAnotherWrongAnswer() throws Exception {
		// given
		Response wrongResponse = builder().withIdentifier("wrongResponseId").withCorrectAnswers("CorrectAnswer2").withCurrentUserAnswers("w").build();

		Map<String, Response> responsesMap = convertToMap(wrongResponse);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		// first call to generate processor state
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// change user answers to other correct answer
		setUpCurrentUserAnswers(wrongResponse, "other wrong response");

		// second call to verify correct behavior
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, LASTMISTAKEN), outcomes);
		assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, LASTMISTAKEN), outcomes);
	}

	@Test
	public void shouldRecognizeCorrectCommutativeAnswers() throws Exception {
		// given
		Response responseWithCorrectAnswer = builder().withCorrectAnswers("CorrectAnswerFIRST").withCurrentUserAnswers("CorrectAnswerSECOND").build();

		Response anotherResponseWithCorrectAnswer = builder()
				.withIdentifier("otherResponseIdentifier")
				.withCorrectAnswers("CorrectAnswerSECOND")
				.withCurrentUserAnswers("CorrectAnswerFIRST")
				.build();

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
	public void shouldRecognizeAnswerAlreadyUsedInGroup() throws Exception {
		// given
		Response responseWithCorrectAnswerInGroup = builder().withCorrectAnswers("CorrectAnswerFIRST").withCurrentUserAnswers("CorrectAnswerSECOND").build();

		Response responseWithAlreadyUsedAnswer = builder()
				.withIdentifier("otherResponseIdentifier")
				.withCorrectAnswers("CorrectAnswerSECOND")
				.withCurrentUserAnswers("CorrectAnswerSECOND")
				.build();

		Map<String, Response> responsesMap = convertToMap(responseWithCorrectAnswerInGroup, responseWithAlreadyUsedAnswer);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("2"), Lists.newArrayList(TODO), outcomes);
	}

	private ResponseBuilder builder() {
		ResponseBuilder builder = new ResponseBuilder();
		builder.withGroups("group1").withCardinality(Cardinality.SINGLE);
		return builder;
	}
}
