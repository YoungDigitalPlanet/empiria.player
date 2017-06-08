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
