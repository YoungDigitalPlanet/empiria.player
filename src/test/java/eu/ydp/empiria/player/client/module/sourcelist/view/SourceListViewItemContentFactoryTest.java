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

package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.gwtutil.client.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType.COMPLEX_TEXT;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SourceListViewItemContentFactoryTest {

    @InjectMocks
    private SourceListViewItemContentFactory testObj;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    @Mock
    private XMLParser xmlParser;
    @Mock
    private Element element;

    @Before
    public void init() {
        Document document = mock(Document.class);
        when(xmlParser.parse(anyString())).thenReturn(document);
        when(document.getDocumentElement()).thenReturn(element);
    }

    @Test
    public void shouldGenerateInlineBody_whenTypeIsMath() {
        // given
        String content = "content";
        SourcelistItemValue sourcelistItemValue = new SourcelistItemValue(COMPLEX_TEXT, content, "some id");

        // when
        testObj.createSourceListContentWidget(sourcelistItemValue, inlineBodyGeneratorSocket);

        // then
        verify(inlineBodyGeneratorSocket).generateInlineBody(element);
    }
}
