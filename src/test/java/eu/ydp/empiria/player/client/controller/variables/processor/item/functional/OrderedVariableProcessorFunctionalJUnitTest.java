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
import eu.ydp.gwtutil.client.Pair;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;
import static junitparams.JUnitParamsRunner.$;

@RunWith(JUnitParamsRunner.class)
@SuppressWarnings("PMD")
public class OrderedVariableProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase {
    private Pair<Map<String, Response>, Map<String, Outcome>> getResponseAndOutcomeMaps(Iterable<String> currentUserAnswers, Iterable<String> correctAnswers) {
        Response aResponse = builder().withIdentifier("a").withCurrentUserAnswers(currentUserAnswers).withCorrectAnswers(correctAnswers).build();

        Map<String, Response> responsesMap = convertToMap(aResponse);
        Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);
        return new Pair<Map<String, Response>, Map<String, Outcome>>(responsesMap, outcomes);
    }

    @Test
    public void shouldRecognizeCorrectOrderOfAnswers() throws Exception {
        // given
        Pair<Map<String, Response>, Map<String, Outcome>> responseAndOutcomeMaps = getResponseAndOutcomeMaps(Arrays.asList("B", "O", "O", "K"),
                Arrays.asList("B", "O", "O", "K"));
        // when
        defaultVariableProcessor.processResponseVariables(responseAndOutcomeMaps.getOne(), responseAndOutcomeMaps.getTwo(), processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES), responseAndOutcomeMaps.getTwo());
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.CORRECT.toString()), Lists.newArrayList(LASTMISTAKEN), responseAndOutcomeMaps.getTwo());
        assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE), responseAndOutcomeMaps.getTwo());
    }

    @Test
    @Parameters(method = "testParams")
    public void shouldRecognizeWrongOrderOfAnswers(List<String> currentUserAnswers, List<String> correctAnswers) throws Exception {
        // given
        Pair<Map<String, Response>, Map<String, Outcome>> responseAndOutcomeMaps = getResponseAndOutcomeMaps(currentUserAnswers, correctAnswers);
        // when
        defaultVariableProcessor.processResponseVariables(responseAndOutcomeMaps.getOne(), responseAndOutcomeMaps.getTwo(), processingMode);

        // then
        assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE, ERRORS, MISTAKES), responseAndOutcomeMaps.getTwo());
        assertGlobalOutcomesHaveValue(Lists.newArrayList(LastMistaken.NONE.toString()), Lists.newArrayList(LASTMISTAKEN), responseAndOutcomeMaps.getTwo());
    }

    public Object[] testParams() {
        return $($(Arrays.asList("B", "O", "O", "K"), Arrays.asList("B", "O", "K", "K")),
                $(Arrays.asList("B", "O", "", "K"), Arrays.asList("B", "", "K", "K")), $(Arrays.asList("", "", "", ""), Arrays.asList("B", "O", "K", "K")));
    }

    private ResponseBuilder builder() {
        return new ResponseBuilder().withCardinality(Cardinality.ORDERED);
    }

}
