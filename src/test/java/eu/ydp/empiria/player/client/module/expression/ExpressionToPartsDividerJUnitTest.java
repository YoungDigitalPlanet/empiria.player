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

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.expression.evaluate.ResponseValuesFetcherFunctions;
import eu.ydp.empiria.player.client.module.expression.exception.CannotDivideExpressionToPartsException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpressionToPartsDividerJUnitTest {

    private final ExpressionToPartsDivider expressionToPartsDivider = new ExpressionToPartsDivider();

    @Test
    public void shouldDivideExpressionWhenEqualSignIsInTemplate_correcAnswer() throws Exception {
        String leftPart = "a+'b'*c:u";
        String rightPart = "7-8+3*2";
        String template = leftPart + ExpressionToPartsDivider.EQUAL_SIGN + rightPart;
        List<Response> responses = new ArrayList<Response>();

        List<String> returnedParts = expressionToPartsDivider.divideExpressionOnEquality(template, responses,
                new ResponseValuesFetcherFunctions().getCorrectAnswerFetcher());

        assertEquals(2, returnedParts.size());
        assertTrue(returnedParts.contains(leftPart));
        assertTrue(returnedParts.contains(rightPart));
    }

    @Test
    public void shouldDivideExpressionWhenEqualSignIsInTemplate_userAnswer() throws Exception {
        String leftPart = "a+'b'*c:u";
        String rightPart = "7-8+3*2";
        String template = leftPart + ExpressionToPartsDivider.EQUAL_SIGN + rightPart;
        List<Response> responses = new ArrayList<Response>();

        List<String> returnedParts = expressionToPartsDivider.divideExpressionOnEquality(template, responses,
                new ResponseValuesFetcherFunctions().getUserAnswerFetcher());

        assertEquals(2, returnedParts.size());
        assertTrue(returnedParts.contains(leftPart));
        assertTrue(returnedParts.contains(rightPart));
    }

    @Test
    public void shouldCorrectlyDivideExpressionWhenEqualSignShouldComeFromResponse_correcAnswer() throws Exception {
        String leftPart = "'a'";
        String rightPart = "'c'";
        String template = leftPart + "'sign'" + rightPart;

        List<Response> responses = new ArrayList<Response>();
        responses.add(new ResponseBuilder().withCorrectAnswers("2").withIdentifier("a").build());
        responses.add(new ResponseBuilder().withCorrectAnswers("8").withIdentifier("c").build());
        responses.add(new ResponseBuilder().withCorrectAnswers(ExpressionToPartsDivider.EQUAL_SIGN).withIdentifier("sign").build());

        List<String> returnedParts = expressionToPartsDivider.divideExpressionOnEquality(template, responses,
                new ResponseValuesFetcherFunctions().getCorrectAnswerFetcher());

        assertEquals(2, returnedParts.size());
        assertTrue(returnedParts.contains(leftPart));
        assertTrue(returnedParts.contains(rightPart));
    }

    @Test
    public void shouldCorrectlyDivideExpressionWhenEqualSignShouldComeFromResponse_userAnswer() throws Exception {
        String leftPart = "'a'";
        String rightPart = "'c'";
        String template = leftPart + "'sign'" + rightPart;

        List<Response> responses = new ArrayList<Response>();
        responses.add(new ResponseBuilder().withCurrentUserAnswers("2").withIdentifier("a").build());
        responses.add(new ResponseBuilder().withCurrentUserAnswers("8").withIdentifier("c").build());
        responses.add(new ResponseBuilder().withCurrentUserAnswers(ExpressionToPartsDivider.EQUAL_SIGN).withIdentifier("sign").build());

        List<String> returnedParts = expressionToPartsDivider.divideExpressionOnEquality(template, responses,
                new ResponseValuesFetcherFunctions().getUserAnswerFetcher());

        assertEquals(2, returnedParts.size());
        assertTrue(returnedParts.contains(leftPart));
        assertTrue(returnedParts.contains(rightPart));
    }

    @Test(expected = CannotDivideExpressionToPartsException.class)
    public void shouldThrowExceptionWhenCannotDivideExpression_correcAnswer() throws Exception {
        String template = "'a'+'c'";

        List<Response> responses = new ArrayList<Response>();
        responses.add(new ResponseBuilder().withCurrentUserAnswers("2").withIdentifier("a").build());
        responses.add(new ResponseBuilder().withCurrentUserAnswers("8").withIdentifier("c").build());

        expressionToPartsDivider.divideExpressionOnEquality(template, responses, new ResponseValuesFetcherFunctions().getUserAnswerFetcher());
    }

}
