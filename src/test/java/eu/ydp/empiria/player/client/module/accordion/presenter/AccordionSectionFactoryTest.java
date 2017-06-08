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

package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class AccordionSectionFactoryTest {

    @InjectMocks
    private AccordionSectionFactory testObj;
    @Mock
    private Provider<AccordionSectionPresenter> sectionProvider;
    @Mock
    private AccordionSectionPresenter section;
    @Mock
    private HasWidgets hasWidgets;
    @Mock
    private AccordionContentGenerator generator;
    @Mock
    private AccordionSectionBean bean;

    @Before
    public void init() {
        when(sectionProvider.get()).thenReturn(section);
        when(section.getContentContainer()).thenReturn(hasWidgets);
    }

    @Test
    public void shouldCreateTitle_andContent() {
        // given
        XMLContent titleXml = mock(XMLContent.class);
        Element title = mock(Element.class);
        when(bean.getTitle()).thenReturn(titleXml);
        when(titleXml.getValue()).thenReturn(title);

        XMLContent contentXml = mock(XMLContent.class);
        Element content = mock(Element.class);
        when(bean.getContent()).thenReturn(contentXml);
        when(contentXml.getValue()).thenReturn(content);

        Widget widget = mock(Widget.class);
        when(generator.generateInlineBody(title)).thenReturn(widget);

        // when
        testObj.createSection(bean, generator);

        // then
        verify(generator).generateBody(content, hasWidgets);
        verify(section).setTitle(widget);
    }
}