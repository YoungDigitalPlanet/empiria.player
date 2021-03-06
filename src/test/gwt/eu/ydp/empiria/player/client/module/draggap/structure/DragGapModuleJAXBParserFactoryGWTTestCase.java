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

package eu.ydp.empiria.player.client.module.draggap.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.module.draggap.standard.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.standard.structure.DragGapModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class DragGapModuleJAXBParserFactoryGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testParseDragGapModule() throws Exception {
        StringBuilder fullXmlStringBuilder = new StringBuilder();
        fullXmlStringBuilder.append("<dragInteraction ");
        fullXmlStringBuilder.append("id=\"id1\" ");
        fullXmlStringBuilder.append("name=\"name1\" ");
        fullXmlStringBuilder.append("expressionMode=\"expressionMode1\" ");
        fullXmlStringBuilder.append("widthBindingGroup=\"widthBindingGroup1\" ");
        fullXmlStringBuilder.append("responseIdentifier=\"responseIdentifier1\" ");
        fullXmlStringBuilder.append("sourcelistId=\"idOfSourceList\">");
        fullXmlStringBuilder.append("</dragInteraction>");
        String xml = fullXmlStringBuilder.toString();

        DragGapBean dragGapBean = parse(xml);
        assertNotNull(dragGapBean);
        assertEquals("id1", dragGapBean.getId());
        assertEquals("name1", dragGapBean.getName());
        assertEquals("expressionMode1", dragGapBean.getExpressionMode());
        assertEquals("widthBindingGroup1", dragGapBean.getWidthBindingGroup());
        assertEquals("responseIdentifier1", dragGapBean.getResponseIdentifier());
        assertEquals("idOfSourceList", dragGapBean.getSourcelistId());
    }

    private DragGapBean parse(String xml) {
        DragGapModuleJAXBParserFactory jaxbParserFactory = GWT.create(DragGapModuleJAXBParserFactory.class);
        JAXBParser<DragGapBean> jaxbParser = jaxbParserFactory.create();
        DragGapBean dragGapBean = jaxbParser.parse(xml);
        return dragGapBean;
    }

}
