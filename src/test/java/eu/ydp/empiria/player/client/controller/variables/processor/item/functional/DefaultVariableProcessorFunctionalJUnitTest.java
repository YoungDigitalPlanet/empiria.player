package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;

public class DefaultVariableProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase {

    @Test
    public void shouldCorrectlyUpdateVariablesWhenCallIsNotAfterUserInteraction() throws Exception {
        // given
        Response correctResponse = new ResponseBuilder().withCardinality(Cardinality.MULTIPLE).withCorrectAnswers("correct1", "correct2", "correct3")
                .withCurrentUserAnswers("correct3", "correct3", "correct3").build();

        Response wrongResponse = new ResponseBuilder().withIdentifier("wrongResponseId").withCardinality(Cardinality.MULTIPLE)
                .withCorrectAnswers("correct1", "correct2", "correct3").withCurrentUserAnswers("wrong", "wrong", "wrong").build();

        Map<String, Response> responsesMap = convertToMap(correctResponse, wrongResponse);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
        ProcessingMode processingMode = ProcessingMode.NOT_USER_INTERACT;

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("2"), Lists.newArrayList(TODO), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE, ERRORS), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(MISTAKES), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);

        assertResponseRelatedOutcomesHaveValue(correctResponse, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(correctResponse, Lists.newArrayList("0"), Lists.newArrayList(MISTAKES, ERRORS), outcomes);
        assertResponseRelatedOutcomesHaveValue(correctResponse, Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);

        assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("1"), Lists.newArrayList(TODO, ERRORS), outcomes);
        assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("0"), Lists.newArrayList(MISTAKES, DONE), outcomes);
        assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
    }

    @Test
    public void shouldHoldStateOfPreviousAnswersAndResetLastInteractionVariables() throws Exception {
        Response wrongResponse = new ResponseBuilder().withCardinality(Cardinality.SINGLE).withCorrectAnswers("correct1").withCurrentUserAnswers("wrong")
                .build();

        Map<String, Response> responsesMap = convertToMap(wrongResponse);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // assert outcomes after first call to processResponses
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(TODO, ERRORS, MISTAKES), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.WRONG.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
        assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList("1"), Lists.newArrayList(TODO, MISTAKES, ERRORS), outcomes);
        assertResponseRelatedOutcomesHaveValue(wrongResponse, Lists.newArrayList(LastMistaken.WRONG.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);

        // second call to processResponses
        Map<String, Outcome> outcomesAfterFirstCall = copyOutcomesMap(outcomes);
        resetLastChangeRelatedVariables(outcomesAfterFirstCall);
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        assertOutcomesMapsAreEqual(outcomesAfterFirstCall, outcomes);
    }

    @Test
    public void shouldRecognizeLastchangeOfAnswer() throws Exception {
        Response response = new ResponseBuilder().withCardinality(Cardinality.SINGLE).withCorrectAnswers("correct").withCurrentUserAnswers("wrong").build();

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
    public void shouldUpdateVariablesAndResetPreviousState() throws Exception {
        // given
        Response response = new ResponseBuilder().withCardinality(Cardinality.SINGLE).withCorrectAnswers("correct").withCurrentUserAnswers("correct").build();

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
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE, MISTAKES), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);

        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(DONE, MISTAKES), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
    }

}
