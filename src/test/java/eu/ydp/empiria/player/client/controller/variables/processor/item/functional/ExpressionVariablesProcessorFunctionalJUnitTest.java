package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponsesMapBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.module.expression.ExpressionToResponseConnector;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpressionVariablesProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase {

    private ExpressionToResponseConnector expressionToResponseConnector;
    private ResponsesMapBuilder responsesMapBuilder = new ResponsesMapBuilder();

    @Override
    @Before
    public void setUp() {
        super.setUp();
        expressionToResponseConnector = injector.getInstance(ExpressionToResponseConnector.class);
    }

    @Test
    public void shouldTestCorrectCalculationsForAdditionAndMultiplication() throws Exception {
        // given
        Response aResponse = builder().withIdentifier("a").withCorrectAnswers("").withCurrentUserAnswers("2").build();

        Response bResponse = builder().withIdentifier("b").withCorrectAnswers("").withCurrentUserAnswers("4").build();

        Response cResponse = builder().withIdentifier("c").withCorrectAnswers("").withCurrentUserAnswers("3").build();

        Response dResponse = builder().withIdentifier("d").withCorrectAnswers("").withCurrentUserAnswers("14").build();

        // filled expression 2+4*3=14
        String expressionTemplate = "'a'+'b'*'c'='d'";
        testVariablesCorrectForExpressionEvaluatedCorrectly(expressionTemplate, aResponse, bResponse, cResponse, dResponse);
    }

    @Test
    public void shouldTestCorrectCalculationsForOperationsDefinedByUser() throws Exception {
        // given
        Response aResponse = builder().withIdentifier("a").withCorrectAnswers("").withCurrentUserAnswers("+").build();

        Response bResponse = builder().withIdentifier("b").withCorrectAnswers("").withCurrentUserAnswers("*").build();

        Response cResponse = builder().withIdentifier("c").withCorrectAnswers("").withCurrentUserAnswers("=").build();

        // filled expression will be 2+3*4=14
        String expressionTemplate = "2'a'3'b'4'c'14";
        testVariablesCorrectForExpressionEvaluatedCorrectly(expressionTemplate, aResponse, bResponse, cResponse);
    }

    @Test
    public void shouldTestCorrectCalculationsForSubstractionAndDivision() throws Exception {
        // given
        Response aResponse = builder().withIdentifier("a").withCorrectAnswers("").withCurrentUserAnswers("4").build();

        Response bResponse = builder().withIdentifier("b").withCorrectAnswers("").withCurrentUserAnswers("2").build();

        Response cResponse = builder().withIdentifier("c").withCorrectAnswers("").withCurrentUserAnswers("1").build();

        Response dResponse = builder().withIdentifier("d").withCorrectAnswers("").withCurrentUserAnswers("1").build();

        // filled expression will be 4:2-1=1
        String expressionTemplate = "'a':'b'-'c'='d'";
        testVariablesCorrectForExpressionEvaluatedCorrectly(expressionTemplate, aResponse, bResponse, cResponse, dResponse);
    }

    private void testVariablesCorrectForExpressionEvaluatedCorrectly(String expressionTemplate, Response... responses) {
        Map<String, Response> responsesMap = convertToMap(responses);
        connectResponsesInOneExpression(expressionTemplate, responsesMap);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.CORRECT.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(String.valueOf(1)), Lists.newArrayList(DONE, TODO), outcomes);

        for (Response response : responses) {
            assertCorrectExpressionEvaluationResult(response, outcomes);
        }
    }

    @Test
    public void shouldRecognizeExpressionNotFilled() throws Exception {
        // given
        Response responseWithEmptyAnswer = builder().withIdentifier("responseWithEmptyAnswer").withCorrectAnswers("").withCurrentUserAnswers("").build();

        Response bResponse = builder().withIdentifier("b").withCorrectAnswers("").withCurrentUserAnswers("15").build();

        Response cResponse = builder().withIdentifier("c").withCorrectAnswers("").withCurrentUserAnswers("27").build();

        String expressionTemplate = "'responseWithEmptyAnswer'+'b'='c'";

        Map<String, Response> responsesMap = convertToMap(responseWithEmptyAnswer, bResponse, cResponse);
        connectResponsesInOneExpression(expressionTemplate, responsesMap);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, DONE), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);

        assertNotFilledExpressionResults(responseWithEmptyAnswer, outcomes);
        assertNotFilledExpressionResults(bResponse, outcomes);
        assertNotFilledExpressionResults(cResponse, outcomes);
    }

    @Test
    public void shouldRecognizeExpressionEvaluationWrong() throws Exception {
        // given
        Response aResponse = builder().withIdentifier("a").withCorrectAnswers("").withCurrentUserAnswers("").build();

        Response bResponse = builder().withIdentifier("b").withCorrectAnswers("").withCurrentUserAnswers("15").build();

        Response cResponse = builder().withIdentifier("c").withCorrectAnswers("").withCurrentUserAnswers("27").build();

        String expressionTemplate = "'a'+'b'='c'";

        Map<String, Response> responsesMap = convertToMap(aResponse, bResponse, cResponse);
        connectResponsesInOneExpression(expressionTemplate, responsesMap);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		/*
         * first call to setup user answers for bResponse and cResponse otherwise processor will threat all 3 responses as new answers, which is impossible to
		 * fill 3 gaps at the same time
		 */
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);
        setUpCurrentUserAnswers(aResponse, "12837");

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(MISTAKES), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.WRONG.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(TODO, ERRORS), outcomes);

        assertWrongExpressionEvaluationResultsForChangedResponse(aResponse, outcomes);
        assertWrongExpressionEvaluationResultsWhenValuesNotChanged(bResponse, outcomes);
        assertWrongExpressionEvaluationResultsWhenValuesNotChanged(cResponse, outcomes);
    }

    private void assertWrongExpressionEvaluationResultsWhenValuesNotChanged(Response response, Map<String, Outcome> outcomes) {
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(DONE, MISTAKES), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, TODO), outcomes);
    }

    private void assertWrongExpressionEvaluationResultsForChangedResponse(Response response, Map<String, Outcome> outcomes) {
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList(LastMistaken.WRONG.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
    }

    private void assertNotFilledExpressionResults(Response response, Map<String, Outcome> outcomes) {
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, DONE), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);
    }

    private void assertCorrectExpressionEvaluationResult(Response response, Map<String, Outcome> outcomes) {
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList(LastMistaken.CORRECT.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
    }

    private void connectResponsesInOneExpression(String expressionTemplate, Map<String, Response> responses) {
        ExpressionBean expressionBean = new ExpressionBean();
        expressionBean.setTemplate(expressionTemplate);

        expressionToResponseConnector.connectResponsesToExpression(expressionBean, responsesMapBuilder.buildResponseManager(responses));
    }

    private ResponseBuilder builder() {
        return new ResponseBuilder().withCheckMode(CheckMode.EXPRESSION);
    }
}
