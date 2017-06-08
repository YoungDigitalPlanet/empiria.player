/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import org.junit.Test;

import java.util.Map;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;

public class SingleCardinalityVariableProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase {

    @Test
    public void shouldRecognizeMistake() throws Exception {
        // given
        Response response = builder().withCorrectAnswers("CorrectAnswer1", "CorrectAnswer2").withCurrentUserAnswers("current not correct answer").build();

        Map<String, Response> responsesMap = convertToMap(response);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, TODO), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.WRONG.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList(LastMistaken.WRONG.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
    }

    @Test
    public void shouldRecognizeCorrectAnswer() throws Exception {
        // given
        Response response = builder().withCorrectAnswers("CorrectAnswer1", "CorrectAnswer2").withCurrentUserAnswers("CorrectAnswer2").build();

        Map<String, Response> responsesMap = convertToMap(response);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.CORRECT.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES), outcomes);
        assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList(LastMistaken.CORRECT.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);
    }

    @Test
    public void shouldTreatEmptyAnswerAsNoAnswerAtAll() throws Exception {
        // given
        Response responseWithEmptyAnswer = builder().withCorrectAnswers("CorrectAnswer").withCurrentUserAnswers("").build();

        Map<String, Response> responsesMap = convertToMap(responseWithEmptyAnswer);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

        // when
        defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, DONE), outcomes);
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), outcomes);

        assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("1"), Lists.newArrayList(TODO), outcomes);
        assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList("0"), Lists.newArrayList(DONE, MISTAKES, ERRORS), outcomes);
        assertResponseRelatedOutcomesHaveValue(responseWithEmptyAnswer, Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN),
                outcomes);
    }

    private ResponseBuilder builder() {
        return new ResponseBuilder().withCardinality(Cardinality.SINGLE);
    }
}
