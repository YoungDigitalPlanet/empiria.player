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

package eu.ydp.empiria.player.client.module.accordion.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.accordion.Transition;

import static eu.ydp.empiria.player.client.module.accordion.structure.AccordionJAXBXmlMock.*;

public class AccordionJAXBParserGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testAccordionShouldContainTwoSections() {
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        Transition transition = bean.getTransition();
        assertEquals(Transition.ALL, transition);

        int sectionsSize = bean.getSections().size();
        assertEquals(2, sectionsSize);
    }

    public void testAccordionShouldContainsGivenTitle_onFirstSection() {
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(0);
        String titleStringValue = sectionBean.getTitle().getValue().toString();
        assertEquals("<title>Click me1!</title>", titleStringValue);
    }

    public void testAccordionShouldContainsGivenTitle_onSecondSection() {
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(1);
        String titleStringValue = sectionBean.getTitle().getValue().toString();
        assertEquals("<title>Click me2!</title>", titleStringValue);
    }

    public void testAccordionShouldContainsGivenContent_onFirstSection() {
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(0);
        String contentStringValue = sectionBean.getContent().getValue().toString();
        assertEquals("<content><simpleText>content</simpleText></content>", contentStringValue);
    }

    public void testAccordionShouldContainsGivenContent_onSecondSection() {
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(1);
        String contentStringValue = sectionBean.getContent().getValue().toString();
        assertEquals("<content><simpleText>content2</simpleText></content>", contentStringValue);
    }

    public void testAccordionTransitionShouldBeSetToWidth() {
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TRANSITION_WIDTH);

        // then
        Transition transition = bean.getTransition();
        assertEquals(Transition.WIDTH, transition);
    }

    public void testAccordionTransitionShouldBeSetToHeight() {
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TRANSITION_HEIGHT);

        // then
        Transition transition = bean.getTransition();
        assertEquals(Transition.HEIGHT, transition);
    }

    private AccordionBean parse(String xml) {
        AccordionJAXBParser jaxbParserFactory = GWT.create(AccordionJAXBParser.class);
        JAXBParser<AccordionBean> jaxbParser = jaxbParserFactory.create();
        AccordionBean accordionBean = jaxbParser.parse(xml);
        return accordionBean;
    }
}