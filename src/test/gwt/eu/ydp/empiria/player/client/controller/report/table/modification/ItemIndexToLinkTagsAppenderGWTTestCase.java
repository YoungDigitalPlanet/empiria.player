package eu.ydp.empiria.player.client.controller.report.table.modification;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class ItemIndexToLinkTagsAppenderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private ItemIndexAppender testObj;

    //@formatter:off
    private final String INPUT = "" +
            "<rd>" +
            "<div class=\"emp_content_icon\">" +
            "<link itemIndex=\"1\">" +
            "<info class=\"info_content_title\"/>" +
            "</link>" +
            "<link>" +
            "<info class=\"info_content_title\"/>" +
            "</link>" +
            "<link url=\"adres\">" +
            "<info class=\"info_content_title\"/>" +
            "</link>" +

            "</div>" +
            "</rd>";

    private final String OUTPUT = "" +
            "<rd>" +
            "<div class=\"emp_content_icon\">" +
            "<link itemIndex=\"1\">" +
            "<info class=\"info_content_title\"/>" +
            "</link>" +
            "<link itemIndex=\"2\">" +
            "<info class=\"info_content_title\"/>" +
            "</link>" +
            "<link url=\"adres\">" +
            "<info class=\"info_content_title\"/>" +
            "</link>" +

            "</div>" +
            "</rd>";

    private Element cellElement;

    //@formatter:on

    @Override
    protected void gwtSetUp() {
        testObj = new ItemIndexAppender();
    }

    public void testAppendToLinkTags() {
        // given
        int ITEM_INDEX = 2;
        cellElement = XMLParser.parse(INPUT).getDocumentElement();

        // when
        testObj.appendToLinkTags(ITEM_INDEX, cellElement);

        // then
        assertEquals(cellElement.toString(), OUTPUT);
    }
}
