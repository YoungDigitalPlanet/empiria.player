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

package eu.ydp.empiria.player.client.module.colorfill.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

import java.util.List;

public class ColorfillInteractionModuleJAXBParserFactoryGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private static final String COLOR_FF0000 = "#FF0000";
    private static final String COLOR_008000 = "#008000";
    private static final String VAL_999 = "999";
    private static final String VAL_777 = "777";
    private static final String VAL_666 = "666";
    private static final String VAL_333 = "333";
    private static final String MEDIA_COLORFILL_PNG = "media/colorfill.png";
    private static final int VAL_400 = 400;
    private static final int VAL_600 = 600;

    public void testXMLParseButtons() {
        // given
        String buttonsXMLNode = "<buttons>" + "<button rgb=\"" + COLOR_008000 + "\"><![CDATA[Zielony]]></button>" + "<button rgb=\"" + COLOR_FF0000
                + "\"><![CDATA[Czerwony]]></button>" + "<eraserButton><![CDATA[Gumka]]></eraserButton>" + "</buttons>";
        String xml = prepareXML(buttonsXMLNode);

        // when
        ButtonsContainer buttonsContainer = getButtonsContainer(xml);
        List<ColorButton> buttons = buttonsContainer.getButtons();
        ColorButton button0 = buttons.get(0);
        ColorButton button1 = buttons.get(1);
        EraserButton eraser = buttonsContainer.getEraserButton();

        // then
        assertEquals(COLOR_008000, button0.getRgb());
        assertEquals("Zielony", button0.getDescription());

        assertEquals(COLOR_FF0000, button1.getRgb());
        assertEquals("Czerwony", button1.getDescription());

        assertNotNull(eraser);
        assertEquals("Gumka", eraser.getDescription());
    }

    public void testXMLParseButtonEraser() {
        // given
        String buttonsXMLNode = "<buttons>" + "<button rgb=\"#008000\"/>" + "</buttons>";
        String xml = prepareXML(buttonsXMLNode);

        // when
        ButtonsContainer buttonsContainer = getButtonsContainer(xml);
        List<ColorButton> buttons = buttonsContainer.getButtons();
        ColorButton button0 = buttons.get(0);
        EraserButton eraser = buttonsContainer.getEraserButton();

        // then
        assertEquals(COLOR_008000, button0.getRgb());
        assertNull(eraser);
    }

    public void testXMLParseAreas() {
        // given
        String xml = prepareXML("<buttons></buttons>");

        // when
        ColorfillInteractionBean bean = parse(xml);
        AreaContainer areaSet = bean.getAreas();
        // TODO: getAreas().getAreas() !!! to be changed! ie. getAreaSet().getAreas()
        List<Area> areas = areaSet.getAreas();
        Area area0 = areas.get(0);
        Area area1 = areas.get(1);

        // then
        assertEquals(VAL_333, area0.getX().toString());
        assertEquals(VAL_666, area0.getY().toString());
        assertEquals(VAL_777, area1.getX().toString());
        assertEquals(VAL_999, area1.getY().toString());
    }

    public void testXMLParseFakeAreas() {
        // given
        String xml = prepareXML("<fakeAreas>" + "<area x=\"1\" y=\"2\" />" + "<area x=\"3\" y=\"4\" />" + "</fakeAreas>");

        // when
        ColorfillInteractionBean bean = parse(xml);
        List<Area> fakeAreas = bean.getFakeAreas().getAreas();
        assertEquals(2, fakeAreas.size());

        Area area = fakeAreas.get(0);
        assertEquals(1, (int) area.getX());
        assertEquals(2, (int) area.getY());

        Area area2 = fakeAreas.get(1);
        assertEquals(3, (int) area2.getX());
        assertEquals(4, (int) area2.getY());
    }

    public void testShouldReturnEmptyFakeAreasWhenNoElement() {
        // given
        String noFakeAreasElement = "";
        String xml = prepareXML(noFakeAreasElement);

        // when
        ColorfillInteractionBean bean = parse(xml);
        assertNotNull(bean.getFakeAreas());
        List<Area> fakeAreas = bean.getFakeAreas().getAreas();
        assertEquals(0, fakeAreas.size());
    }

    public void testXMLParseImage() {
        // given
        String xml = prepareXML("<buttons></buttons>");

        // when
        ColorfillInteractionBean bean = parse(xml);
        Image image = bean.getImage();

        // then
        assertEquals(VAL_400, image.getHeight());
        assertEquals(VAL_600, image.getWidth());
        assertEquals(MEDIA_COLORFILL_PNG, image.getSrc());
    }

    private ButtonsContainer getButtonsContainer(String xml) {
        ColorfillInteractionBean bean = parse(xml);
        // TODO: getButtons().getButtons() !!! to be changed! ie. getButtonSet().getButtons()
        ButtonsContainer buttonsContainer = bean.getButtons();
        return buttonsContainer;
    }

    private ColorfillInteractionBean parse(String xml) {
        ColorfillInteractionModuleJAXBParserFactory jaxbParserFactory = GWT.create(ColorfillInteractionModuleJAXBParserFactory.class);
        JAXBParser<ColorfillInteractionBean> jaxbParser = jaxbParserFactory.create();
        ColorfillInteractionBean expressionsBean = jaxbParser.parse(xml);
        return expressionsBean;
    }

    private String prepareXML(String content) {
        return "<colorfillInteraction id=\"dummy1\">" + content + "<areas>" + "<area x=\"" + VAL_333 + "\" y=\"" + VAL_666 + "\"/>" + "<area x=\"" + VAL_777
                + "\" y=\"" + VAL_999 + "\"/>" + "</areas>" + "<image height=\"" + VAL_400 + "\" src=\"media/colorfill.png\" width=\"" + VAL_600 + "\"/>"
                + "</colorfillInteraction>";
    }

}
