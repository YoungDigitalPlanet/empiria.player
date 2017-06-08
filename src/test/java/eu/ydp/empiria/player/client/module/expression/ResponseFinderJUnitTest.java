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

package eu.ydp.empiria.player.client.module.expression;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.expression.evaluate.ResponseValuesFetcherFunctions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ResponseFinderJUnitTest {

    private ResponseFinder identifierToValueConverter;
    private ResponseValuesFetcherFunctions responseValuesFetcherFunctions;

    @Before
    public void before() {
        identifierToValueConverter = new ResponseFinder(new ExpressionToPartsDivider(), new IdentifiersFromExpressionExtractor());
        responseValuesFetcherFunctions = new ResponseValuesFetcherFunctions();
    }

    @Test
    public void getCorectValuesTest() {
        List<String> responseIdentifiers = Lists.newArrayList("a", "b", "c");
        List<Response> responses = Lists.newArrayList(getResponses("c", "1"), getResponses("b", "34"), getResponses("a", "3"));

        List<String> corectValues = identifierToValueConverter.getCorectValues(responseIdentifiers, responses,
                responseValuesFetcherFunctions.getCorrectAnswerFetcher());

        assertTrue(corectValues.contains("1"));
        assertTrue(corectValues.contains("34"));
        assertTrue(corectValues.contains("3"));
    }

    private Response getResponses(String idResponse, String correctValue) {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.withCorrectAnswers(correctValue);
        responseBuilder.withIdentifier(idResponse);
        return responseBuilder.build();
    }
}
