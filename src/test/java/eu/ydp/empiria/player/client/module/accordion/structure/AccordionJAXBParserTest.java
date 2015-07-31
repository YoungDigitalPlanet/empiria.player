package eu.ydp.empiria.player.client.module.accordion.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.accordion.Transition;

import static eu.ydp.empiria.player.client.module.accordion.structure.AccordionJAXBXmlMock.*;

public class AccordionJAXBParserTest extends EmpiriaPlayerGWTTestCase {

    public void testAccordionShouldContainTwoSections() {
        // given
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        Transition transition = bean.getTransition();
        assertEquals(Transition.ALL, transition);

        int sectionsSize = bean.getSections().size();
        assertEquals(2, sectionsSize);
    }

    public void testAccordionShouldContainsGivenTitle_onFirstSection() {
        // given
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(0);
        String titleStringValue = sectionBean.getTitle().getValue().toString();
        assertEquals("<title>Click me1!</title>", titleStringValue);
    }

    public void testAccordionShouldContainsGivenTitle_onSecondSection() {
        // given
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(1);
        String titleStringValue = sectionBean.getTitle().getValue().toString();
        assertEquals("<title>Click me2!</title>", titleStringValue);
    }

    public void testAccordionShouldContainsGivenContent_onFirstSection() {
        // given
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(0);
        String contentStringValue = sectionBean.getContent().getValue().toString();
        assertEquals("<content><simpleText>content</simpleText></content>", contentStringValue);
    }

    public void testAccordionShouldContainsGivenContent_onSecondSection() {
        // given
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TWO_SECTIONS);

        // then
        AccordionSectionBean sectionBean = bean.getSections().get(1);
        String contentStringValue = sectionBean.getContent().getValue().toString();
        assertEquals("<content><simpleText>content2</simpleText></content>", contentStringValue);
    }

    public void testAccordionTransitionShouldBeSetToWidth() {
        // given
        // when
        AccordionBean bean = parse(ACCORDION_WITH_TRANSITION_WIDTH);

        // then
        Transition transition = bean.getTransition();
        assertEquals(Transition.WIDTH, transition);
    }

    public void testAccordionTransitionShouldBeSetToHeight() {
        // given
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