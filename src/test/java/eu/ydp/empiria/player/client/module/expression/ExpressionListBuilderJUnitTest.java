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
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionsBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionListBuilderJUnitTest {

    private ExpressionListBuilder expressionListBuilder;

    @Mock
    private ExpressionModuleJAXBParserFactory jaxbParserFactory;
    @Mock
    private ExpressionToResponseConnector expressionToResponseConnector;
    @Mock
    private ExpressionSetsFinder expressionSetsFinder;

    @Before
    public void setUp() throws Exception {
        expressionListBuilder = new ExpressionListBuilder(jaxbParserFactory, expressionToResponseConnector, expressionSetsFinder);
    }

    @Test
    public void shouldParseXmlAndConnectResponses() throws Exception {
        // given
        String expressionsXml = "sampleXML";

        ItemResponseManager givenItemResponseManager = mock(ItemResponseManager.class);

        @SuppressWarnings("unchecked")
        JAXBParser<ExpressionsBean> jaxbParser = Mockito.mock(JAXBParser.class);
        when(jaxbParserFactory.create()).thenReturn(jaxbParser);

        ExpressionsBean expressionsBean = new ExpressionsBean();
        ExpressionBean expressionBean = new ExpressionBean();
        expressionBean.setMode(ExpressionMode.COMMUTATION);
        List<ExpressionBean> expressions = Lists.newArrayList(expressionBean);
        expressionsBean.setExpressions(expressions);
        when(jaxbParser.parse(expressionsXml)).thenReturn(expressionsBean);

        // when
        List<ExpressionBean> returnedExpressions = expressionListBuilder.parseAndConnectExpressions(expressionsXml, givenItemResponseManager);

        // then
        assertEquals(expressions, returnedExpressions);
        verify(jaxbParserFactory).create();
        verify(jaxbParser).parse(expressionsXml);
        verify(expressionToResponseConnector).connectResponsesToExpression(expressionBean, givenItemResponseManager);
        verify(expressionSetsFinder).updateResponsesSetsInExpression(expressionBean);
        Mockito.verifyNoMoreInteractions(jaxbParserFactory, expressionToResponseConnector, jaxbParser);
    }
}
