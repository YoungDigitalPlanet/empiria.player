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
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpressionValidatorTest {

    private ExpressionValidator expressionValidator;

    @Before
    public void before() {
        expressionValidator = new ExpressionValidator();
    }

    @Test
    public void isAllResponsesAreNotEmptyTest_responsesListIsEmpty() {
        ExpressionBean expressionBean = new ExpressionBean();

        boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

        assertFalse(result);
    }

    @Test
    public void isAllResponsesAreNotEmptyTest_responseHasValueListNull() {
        ExpressionBean expressionBean = new ExpressionBean();

        List<Response> responses = expressionBean.getResponses();
        Response response = getEmptyResponse();
        responses.add(response);

        boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

        assertFalse(result);
    }

    @Test
    public void isAllResponsesAreNotEmptyTest_responseHasValueListEmpty() {
        ExpressionBean expressionBean = new ExpressionBean();

        List<Response> responses = expressionBean.getResponses();
        Response response = getEmptyResponse();
        response.values = new ArrayList<String>();

        responses.add(response);

        boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

        assertFalse(result);
    }

    @Test
    public void isAllResponsesAreNotEmptyTest_responseHasValueListElementEmpty() {
        ExpressionBean expressionBean = new ExpressionBean();

        List<Response> responses = expressionBean.getResponses();
        Response response = getEmptyResponse();
        response.values = new ArrayList<String>();
        response.values.add("ok");
        responses.add(response);

        response = getEmptyResponse();
        response.values = new ArrayList<String>();
        response.values.add("");
        responses.add(response);

        boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

        assertFalse(result);
    }

    @Test
    public void isAllResponsesAreNotEmptyTest_shouldByOk() {
        ExpressionBean expressionBean = new ExpressionBean();

        List<Response> responses = expressionBean.getResponses();
        Response response = getEmptyResponse();
        response.values = new ArrayList<String>();
        response.values.add("ok");

        responses.add(response);

        boolean result = expressionValidator.isAllResponsesAreNotEmpty(expressionBean);

        assertTrue(result);
    }

    private Response getEmptyResponse() {
        return new ResponseBuilder().build();
    }
}
