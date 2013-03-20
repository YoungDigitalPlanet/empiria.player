package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import java.util.Map;

import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;

public class MultipleCardinalityVariablesProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase{

	@Test
	public void shouldRecognizeCorrectAnswer() throws Exception {
		// given
		Response response = builder()
				.withCorrectAnswers("CorrectAnswer1", "CorrectAnswer2")
				.withCurrentUserAnswers("CorrectAnswer2", "CorrectAnswer1")
				.build();

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
	public void shouldRecognizeMistake() throws Exception {
		// given
		Response response = builder()
				.withCorrectAnswers("CorrectAnswer1", "CorrectAnswer2")
				.withCurrentUserAnswers("current not correct answer")
				.build();

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
	public void shouldTreatResponseWithOnlyEmptyAnswersAsNoAnswerAtAll() throws Exception {
		// given
		Response responseWithEmptyAnswer = builder()
				.withCorrectAnswers("CorrectAnswer")
				.withCurrentUserAnswers("", "", "")
				.build();

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
	
	private ResponseBuilder builder(){
		return new ResponseBuilder().withCardinality(Cardinality.MULTIPLE);
	}
}
