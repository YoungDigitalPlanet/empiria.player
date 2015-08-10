package eu.ydp.empiria.player.client.module.accordion.structure;

public class AccordionJAXBXmlMock {

    public static final String ACCORDION_WITH_TWO_SECTIONS = "<accordion transition=\"all\">\n" +
            "\t<section>\n" +
            "\t<title>Click me1!</title>\n" +
            "\t<content><simpleText>content</simpleText></content>" +
            "\t</section>\n" +
            "\t<section>\n" +
            "\t<title>Click me2!</title>\n" +
            "\t<content><simpleText>content2</simpleText></content>\n" +
            "\t</section>\n" +
            "\t</accordion>";

    public static final String ACCORDION_WITH_TRANSITION_WIDTH = "<accordion transition=\"width\">\n" +
            "\t<section>\n" +
            "\t<title>Click me1!</title>\n" +
            "\t<content><simpleText>content</simpleText></content>" +
            "\t</section>\n" +
            "\t</accordion>";

    public static final String ACCORDION_WITH_TRANSITION_HEIGHT = "<accordion transition=\"height\">\n" +
            "\t<section>\n" +
            "\t<title>Click me1!</title>\n" +
            "\t<content><simpleText>content</simpleText></content>" +
            "\t</section>\n" +
            "\t</accordion>";
}
