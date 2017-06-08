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

package eu.ydp.empiria.player.client.controller.item;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.expression.ExpressionListBuilder;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.gwtutil.client.xml.NodeListIterable;
import eu.ydp.gwtutil.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
@RunWith(MockitoJUnitRunner.class)
public class ItemExpressionParserJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    final String xml = "<expressions>" + "<expression mode=\"commutation\">" + "<![CDATA['a'+'b'=6]]>" + "</expression>" + "<expression mode=\"default\">"
            + "<![CDATA[3'sign'5=15]]>" + "</expression>" + "<expression>" + "<![CDATA[3'sign'5=15]]>" + "</expression>" + "</expressions>";

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(ExpressionListBuilder.class).toInstance(expressionListBuilder);
            binder.bind(XmlData.class).annotatedWith(PageScoped.class).toInstance(xmlData);
            binder.bind(ItemResponseManager.class).annotatedWith(PageScoped.class).toInstance(responseManager);
            binder.bind(ItemXMLWrapper.class).annotatedWith(PageScoped.class).to(ItemXMLWrapper.class);
        }
    }

    @Mock
    private ExpressionListBuilder expressionListBuilder;
    @Mock
    private XmlData xmlData;
    @Mock
    private ItemResponseManager responseManager;

    private ItemExpressionParser instance;
    Document document = spy(XMLParser.parse(xml));

    @Before
    public void before() {
        doReturn(document).when(xmlData).getDocument();
        setUp(new GuiceModuleConfiguration(), new CustomGinModule());
        instance = injector.getInstance(ItemExpressionParser.class);

    }

    @Test
    public void parseAndConnectExpressions() throws Exception {
        instance.parseAndConnectExpressions();
        verify(document).getElementsByTagName("expressions");
        NodeListIterable iterator = new NodeListIterable(document.getElementsByTagName("expressions"));
        for (Node node : iterator) {
            verify(expressionListBuilder).parseAndConnectExpressions(Matchers.eq(node.toString()), isA(ItemResponseManager.class));
        }

    }

}
