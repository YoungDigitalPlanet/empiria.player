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
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class ItemXMLWrapperJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(XmlData.class).annotatedWith(PageScoped.class).toInstance(xmlData);
        }
    }

    private final XmlData xmlData = mock(XmlData.class);
    private final Document document = mock(Document.class);
    private ItemXMLWrapper instance;

    @Before
    public void before() {
        setUp(new GuiceModuleConfiguration(), new CustomGinModule());
        NodeList nodeList = mock(NodeList.class);
        when(nodeList.item(Matchers.anyInt())).thenReturn(mock(Element.class));
        when(document.getElementsByTagName(Matchers.anyString())).thenReturn(nodeList);
        doReturn(document).when(xmlData).getDocument();
        doReturn("url").when(xmlData).getBaseURL();
        instance = injector.getInstance(ItemXMLWrapper.class);
    }

    @Test
    public void postConstruct() throws Exception {
        verify(xmlData).getDocument();
    }

    @Test
    public void getStyleDeclaration() throws Exception {
        assertThat(instance.getStyleDeclaration()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("styleDeclaration"));
    }

    @Test
    public void getAssessmentItems() throws Exception {
        assertThat(instance.getAssessmentItems()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("assessmentItem"));
    }

    @Test
    public void getItemBody() throws Exception {
        assertThat(instance.getItemBody()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("itemBody"));
    }

    @Test
    public void getResponseDeclarations() throws Exception {
        assertThat(instance.getResponseDeclarations()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("responseDeclaration"));
    }

    @Test
    public void getExpressions() throws Exception {
        assertThat(instance.getExpressions()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("expressions"));
    }

    @Test
    public void getBaseURL() throws Exception {
        assertThat(instance.getBaseURL()).isEqualTo("url");
        verify(xmlData).getBaseURL();
    }

}
