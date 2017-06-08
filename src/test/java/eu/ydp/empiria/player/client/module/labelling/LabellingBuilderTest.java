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

package eu.ydp.empiria.player.client.module.labelling;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingChildView;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;
import eu.ydp.gwtutil.xml.XMLParser;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.*;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LabellingBuilderTest {

    private LabellingBuilder testObj;

    @Mock
    private LabellingModuleJAXBParserFactory parserFactory;
    @Mock
    private Provider<LabellingView> viewProvider;
    @Mock
    private Provider<LabellingChildView> childViewProvider;
    @Mock
    private LabellingView view;
    @Mock
    private LabellingChildView childView;

    @Before
    public void setUp() {
        XMLUnit.setIgnoreWhitespace(true);

        when(viewProvider.get()).thenReturn(view);
        when(childViewProvider.get()).thenReturn(childView);
        when(parserFactory.create()).thenReturn(new LabellingInteractionBeanMockParser(CHILDREN_FULL));
        testObj = new LabellingBuilder(parserFactory, new LabellingViewBuilder(viewProvider, childViewProvider));

        stub(view.getContainer()).toReturn(mock(ForIsWidget.class));
    }

    @Test
    public void test() throws SAXException, IOException {
        // given
        BodyGeneratorSocket bgs = mock(BodyGeneratorSocket.class);
        Element element = XMLParser.parse(XML_FULL).getDocumentElement();

        // when
        LabellingView view = testObj.build(element, bgs);

        // then
        verify(view).setBackground(eq(IMAGE_BEAN));
        verify(view).setViewId(eq(ID));
        verifyChildrenPositioning(view, CHILDREN_FULL);
        verifyChildrenGeneration(bgs, CHILDREN_FULL);

    }

    @Test
    public void test_noChildren() throws SAXException, IOException {
        // given
        BodyGeneratorSocket bgs = mock(BodyGeneratorSocket.class);
        Element element = XMLParser.parse(XML_NO_CHILDREN).getDocumentElement();

        // when
        LabellingView view = testObj.build(element, bgs);

        // then
        verify(view).setBackground(eq(IMAGE_BEAN));
        verify(view, never()).addChild(any(IsWidget.class), anyInt(), anyInt());

    }

    @Test
    public void test_noChildrenNode() throws SAXException, IOException {
        // given
        BodyGeneratorSocket bgs = mock(BodyGeneratorSocket.class);
        Element element = XMLParser.parse(XML_NO_CHILDREN_NODE).getDocumentElement();

        // when
        LabellingView view = testObj.build(element, bgs);

        // then
        verify(view).setBackground(eq(IMAGE_BEAN));
        verify(view, never()).addChild(any(IsWidget.class), anyInt(), anyInt());

    }

    private void verifyChildrenPositioning(LabellingView view, List<ChildData> children) {
        ArgumentCaptor<Integer> leftCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> topCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(view, times(children.size())).addChild(any(IsWidget.class), leftCaptor.capture(), topCaptor.capture());

        assertThat(leftCaptor.getAllValues(), equalTo(CHILDREN_FULL_X));
        assertThat(topCaptor.getAllValues(), equalTo(CHILDREN_FULL_Y));
    }

    private void verifyChildrenGeneration(BodyGeneratorSocket bgs, List<ChildData> children) throws SAXException, IOException {
        int childrenCount = children.size();

        ArgumentCaptor<Node> nodeCaptor = ArgumentCaptor.forClass(Node.class);
        verify(bgs, times(childrenCount)).generateBody(nodeCaptor.capture(), any(HasWidgets.class));

        Iterator<Node> iterator = nodeCaptor.getAllValues().iterator();
        for (ChildData child : children) {
            assertXMLEqual(child.xml(), iterator.next().toString());
        }
    }

}
