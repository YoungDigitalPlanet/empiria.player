package eu.ydp.empiria.player.client.module.button.download.structure;

import com.google.gwt.core.shared.GWT;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class ButtonBeanJAXBParserFactoryGWTTestCase extends EmpiriaPlayerGWTTestCase {
    private static final String HREF = "media/image.png";
    private static final String ALT = "Link to image file";
    private static final String ID = "dummy1";
    String xml = "<button alt=\"" + ALT + "\" href=\"" + HREF + "\" id=\"" + ID + "\"/>";

    public void testXMLParser() {
        ButtonBean buttonBean = parseXml(xml);
        assertEquals(buttonBean.getAlt(), ALT);
        assertEquals(buttonBean.getId(), ID);
        assertEquals(buttonBean.getHref(), HREF);
    }

    private ButtonBean parseXml(String xml) {
        ButtonModuleJAXBParser parserFactory = GWT.create(ButtonModuleJAXBParser.class);
        return parserFactory.create().parse(xml);
    }
}
