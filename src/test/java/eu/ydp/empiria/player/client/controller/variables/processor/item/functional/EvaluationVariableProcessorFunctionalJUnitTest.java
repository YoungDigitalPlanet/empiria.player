package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;

public class EvaluationVariableProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase {

    @Test
    public void shouldCorrectlyEvaluateAnswersInEvaluateUserMode() throws Exception {
        // given
        Response response = new ResponseBuilder().withCardinality(Cardinality.MULTIPLE).withEvaluate(Evaluate.USER)
                .withCorrectAnswers("correct1", "correct2", "correct3").withCurrentUserAnswers("correct2", "wrongAnswer", "correct1").build();

        List<Boolean> expectedAnswersEvaluation = Lists.newArrayList(true, false, true);

        Map<String, Response> responsesMap = convertToMap(response);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
        List<Boolean> evaluatedAnswer = answerEvaluationProvider.evaluateAnswer(response);

        // then
        assertEquals(expectedAnswersEvaluation, evaluatedAnswer);
    }

    @Test
    public void shouldCorrectlyEvaluateAnswersInEvaluateCorrectMode() throws Exception {
        // given
        Response response = new ResponseBuilder().withCardinality(Cardinality.MULTIPLE).withEvaluate(Evaluate.CORRECT)
                .withCorrectAnswers("correct1", "correct2", "correct3").withCurrentUserAnswers("wrongAnswer", "correct3", "correct3").build();

        List<Boolean> expectedAnswersEvaluation = Lists.newArrayList(false, false, true);

        Map<String, Response> responsesMap = convertToMap(response);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
        ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
        List<Boolean> evaluatedAnswer = answerEvaluationProvider.evaluateAnswer(response);

        // then
        assertEquals(expectedAnswersEvaluation, evaluatedAnswer);
    }

    @Test
    public void shouldCorrectlyEvaluateForGroupedAnswers() throws Exception {
        // given
        Response correctResponse = new ResponseBuilder().withCardinality(Cardinality.SINGLE).withCorrectAnswers("correct1")
                .withCurrentUserAnswers("correct2", "wrongAnswer").withGroups("group1").build();

        Response groupedResponse = new ResponseBuilder().withIdentifier("groupedResponseId").withCardinality(Cardinality.SINGLE).withCorrectAnswers("correct2")
                .withGroups("group1").build();

        Map<String, Response> responsesMap = convertToMap(groupedResponse, correctResponse);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
        List<Boolean> evaluatedAnswer = answerEvaluationProvider.evaluateAnswer(correctResponse);
        // then
        assertThat(evaluatedAnswer, contains(true, false));
    }

}
