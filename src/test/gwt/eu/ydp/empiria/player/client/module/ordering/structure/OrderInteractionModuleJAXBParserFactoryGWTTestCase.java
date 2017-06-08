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

package eu.ydp.empiria.player.client.module.ordering.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

@SuppressWarnings("PMD")
public class OrderInteractionModuleJAXBParserFactoryGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private final String XML_BODY = "<simpleChoice identifier=\"ORDERING_RESPONSE_1_0\">" + "Words in this sentence	" + "</simpleChoice>"
            + "<simpleChoice identifier=\"ORDERING_RESPONSE_1_1\" fixed=\"true\">" + "should be put in" + "</simpleChoice>"
            + "<simpleChoice identifier=\"ORDERING_RESPONSE_1_2\" fixed=\"false\">" + "the correct order." + "</simpleChoice>";

    private final String XML = "<orderInteraction id=\"dummy1\" responseIdentifier=\"ORDERING_RESPONSE_1\" shuffle=\"true\">" + XML_BODY
            + "</orderInteraction>";

    private final String XML_WITH_ORIENTATION_VERTICAL = "<orderInteraction  orientation=\"vertical\" id=\"dummy1\" responseIdentifier=\"ORDERING_RESPONSE_1\" shuffle=\"true\">"
            + XML_BODY + "</orderInteraction>";

    private final String XML_WITH_ORIENTATION_HORIZONTAL = "<orderInteraction  orientation=\"horizontal\" id=\"dummy1\" responseIdentifier=\"ORDERING_RESPONSE_1\" shuffle=\"true\">"
            + XML_BODY + "</orderInteraction>";

    public void testXMLParse() {
        // dummy test sprawdzenie czy bindowanie dziala
        OrderInteractionBean bean = parse(XML);
        assertEquals(3, bean.getChoiceBeans().size());
        assertEquals("dummy1", bean.getId());
        assertEquals("ORDERING_RESPONSE_1", bean.getResponseIdentifier());
        assertEquals(true, bean.isShuffle());
        assertEquals(bean.getOrientation(), OrderInteractionOrientation.HORIZONTAL);

        SimpleOrderChoiceBean simpleOrderChoiceBean = bean.getChoiceBeans().get(0);
        assertEquals("ORDERING_RESPONSE_1_0", simpleOrderChoiceBean.getIdentifier());
        assertEquals(false, simpleOrderChoiceBean.isFixed());
        assertNotNull(simpleOrderChoiceBean.getContent().getValue());

        simpleOrderChoiceBean = bean.getChoiceBeans().get(1);
        assertEquals("ORDERING_RESPONSE_1_1", simpleOrderChoiceBean.getIdentifier());
        assertEquals(true, simpleOrderChoiceBean.isFixed());
        assertNotNull(simpleOrderChoiceBean.getContent().getValue());

        simpleOrderChoiceBean = bean.getChoiceBeans().get(2);
        assertEquals("ORDERING_RESPONSE_1_2", simpleOrderChoiceBean.getIdentifier());
        assertEquals(false, simpleOrderChoiceBean.isFixed());
        assertNotNull(simpleOrderChoiceBean.getContent().getValue());
    }

    public void testXMLParseWithHorizontalOrientation() {
        // dummy test sprawdzenie czy bindowanie dziala
        OrderInteractionBean bean = parse(XML_WITH_ORIENTATION_HORIZONTAL);
        assertEquals(3, bean.getChoiceBeans().size());
        assertEquals("dummy1", bean.getId());
        assertEquals("ORDERING_RESPONSE_1", bean.getResponseIdentifier());
        assertEquals(true, bean.isShuffle());
        assertEquals(bean.getOrientation(), OrderInteractionOrientation.HORIZONTAL);
    }

    public void testXMLParseWithVerticalOrientation() {
        // dummy test sprawdzenie czy bindowanie dziala
        OrderInteractionBean bean = parse(XML_WITH_ORIENTATION_VERTICAL);
        assertEquals(3, bean.getChoiceBeans().size());
        assertEquals("dummy1", bean.getId());
        assertEquals("ORDERING_RESPONSE_1", bean.getResponseIdentifier());
        assertEquals(true, bean.isShuffle());
        assertEquals(bean.getOrientation(), OrderInteractionOrientation.VERTICAL);
    }

    private OrderInteractionBean parse(String xml) {
        OrderInteractionModuleJAXBParserFactory jaxbParserFactory = GWT.create(OrderInteractionModuleJAXBParserFactory.class);
        JAXBParser<OrderInteractionBean> jaxbParser = jaxbParserFactory.create();
        OrderInteractionBean expressionsBean = jaxbParser.parse(xml);
        return expressionsBean;
    }
}
