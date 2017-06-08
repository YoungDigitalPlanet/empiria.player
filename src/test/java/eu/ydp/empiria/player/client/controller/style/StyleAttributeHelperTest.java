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

package eu.ydp.empiria.player.client.controller.style;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.util.BooleanUtils;
import eu.ydp.gwtutil.client.xml.XMLParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class StyleAttributeHelperTest extends AbstractTestBase {

    private Map<String, String> hashMap;

    @Mock
    private XMLParser xmlParser;
    @Mock
    private StyleSocket styleSocket;

    private StyleSocketAttributeHelper instance;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        instance = new StyleSocketAttributeHelper(xmlParser, new BooleanUtils(), styleSocket);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(styleSocket, xmlParser);
    }

    @Test
    public void testGetBoolean_false() {

        Document document = mock(Document.class);
        Element firstChild = mock(Element.class);
        Element element = mock(Element.class);
        when(document.getDocumentElement()).thenReturn(element);
        when(element.getFirstChild()).thenReturn(firstChild);
        String xmlConntent = "<root><nodeName class=\"nodeName\"/></root>";
        when(xmlParser.parse(xmlConntent)).thenReturn(document);
        Map<String, String> map = new HashMap<String, String>();
        map.put("attribute", "false");
        when(styleSocket.getStyles(firstChild)).thenReturn(map);

        boolean attribute = instance.getBoolean("nodeName", "attribute");

        assertFalse(attribute);

        InOrder inOrder = inOrder(styleSocket, xmlParser);
        inOrder.verify(xmlParser).parse(xmlConntent);
        inOrder.verify(styleSocket).getStyles(firstChild);

    }

    @Test
    public void testGetBoolean_true() {

        Document document = mock(Document.class);
        Element firstChild = mock(Element.class);
        Element element = mock(Element.class);
        when(document.getDocumentElement()).thenReturn(element);
        when(element.getFirstChild()).thenReturn(firstChild);
        String xmlConntent = "<root><nodeName class=\"nodeName\"/></root>";
        when(xmlParser.parse(xmlConntent)).thenReturn(document);
        Map<String, String> map = new HashMap<String, String>();
        map.put("attribute", "true");
        when(styleSocket.getStyles(firstChild)).thenReturn(map);

        boolean attribute = instance.getBoolean("nodeName", "attribute");

        assertTrue(attribute);

        InOrder inOrder = inOrder(styleSocket, xmlParser);
        inOrder.verify(xmlParser).parse(xmlConntent);
        inOrder.verify(styleSocket).getStyles(firstChild);
    }
}
