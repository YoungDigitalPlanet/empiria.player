package eu.ydp.empiria.player.client.module.external.interaction.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class ExternalInteractionModuleJAXBParserFactoryTest extends AbstractEmpiriaPlayerGWTTestCase {

    public void testShouldParseExternalInteractionBean() {
        //given
        final String expectedURL = "external/view.html";
        String xml = "<externalInteraction src=\"" + expectedURL + "\"/>";

        ExternalInteractionModuleJAXBParserFactory jaxbParserFactory = GWT.create(ExternalInteractionModuleJAXBParserFactory.class);
        JAXBParser<ExternalInteractionModuleBean> externalInteractionModuleBeanJAXBParser = jaxbParserFactory.create();

        // when
        ExternalInteractionModuleBean actual = externalInteractionModuleBeanJAXBParser.parse(xml);

        // then
        assertEquals(actual.getSrc(), expectedURL);
    }
}
