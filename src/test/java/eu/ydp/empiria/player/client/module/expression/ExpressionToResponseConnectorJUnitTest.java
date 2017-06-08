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

import com.google.common.collect.Lists;
import com.google.gwt.dev.util.collect.HashMap;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionToResponseConnectorJUnitTest {

    private ExpressionToResponseConnector expressionToResponseConnector;

    @Mock
    private IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor;
    @Mock
    private ItemResponseManager itemResponseManager;

    @Before
    public void setUp() throws Exception {
        expressionToResponseConnector = new ExpressionToResponseConnector(identifiersFromExpressionExtractor);
    }

    @Test
    public void shouldConnectOnlyResponsesRelatedToExpression() throws Exception {
        // given
        String template = "template";
        ExpressionBean expressionBean = new ExpressionBean();
        expressionBean.setTemplate(template);

        Response relatedResponse = Mockito.mock(Response.class);
        Response unrelatedResponse = Mockito.mock(Response.class);

        when(itemResponseManager.getVariable("relatedResponse")).thenReturn(relatedResponse);
        when(itemResponseManager.getVariable("unrelatedResponse")).thenReturn(unrelatedResponse);

        when(identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(template)).thenReturn(
                Lists.newArrayList("relatedResponse", "notExistingId"));

        // when
        expressionToResponseConnector.connectResponsesToExpression(expressionBean, itemResponseManager);

        // then
        verify(identifiersFromExpressionExtractor).extractResponseIdentifiersFromTemplate(template);
        verify(relatedResponse).setExpression(expressionBean);
        List<Response> connectedResponses = expressionBean.getResponses();
        assertEquals(1, connectedResponses.size());
        assertEquals(relatedResponse, connectedResponses.get(0));
    }

    @Test
    public void shouldIgnoreIdentifierWhenNoResponseExistsForIt() throws Exception {
        // given
        String template = "template";
        ExpressionBean expressionBean = new ExpressionBean();
        expressionBean.setTemplate(template);

        Response unrelatedResponse = Mockito.mock(Response.class);
        when(itemResponseManager.getVariable("unrelatedResponse")).thenReturn(unrelatedResponse);

        when(identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(template)).thenReturn(Lists.newArrayList("relatedResponse"));

        // when
        expressionToResponseConnector.connectResponsesToExpression(expressionBean, itemResponseManager);

        // then
        verify(identifiersFromExpressionExtractor).extractResponseIdentifiersFromTemplate(template);
        List<Response> connectedResponses = expressionBean.getResponses();
        assertEquals(0, connectedResponses.size());
    }
}
