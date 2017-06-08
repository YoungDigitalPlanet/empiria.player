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

package eu.ydp.empiria.player.client.module.drawing.model;

import com.google.gwt.core.shared.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

import java.util.Arrays;
import java.util.List;

public class DrawingModelProviderGWTTestCase extends EmpiriaPlayerGWTTestCase {
    private static final String ID = "dummy1";
    private static final int WIDTH = 601;
    private static final int HEIGHT = 400;
    private static final String SRC = "media/drawing.png";
    private final List<String> colors = Arrays.asList("ff281e", "0e86d8", "0e86a8");
    private final List<String> colorNames = Arrays.asList("czerwony", "niebieski", "");
    String xml = "<drawing id=\"" + ID + "\">" + "<palette>" + "<color rgb=\"ff281e\"><![CDATA[czerwony]]></color>"
            + "<color rgb=\"0e86d8\"><![CDATA[niebieski]]></color>" + "<color rgb=\"0e86a8\"/>" + "</palette>" + "<image height=\"" + HEIGHT + "\" src=\""
            + SRC + "\" width=\"" + WIDTH + "\"/>" + "</drawing>";

    public void testJAXB() {
        DrawingModuleJAXBParserFactory jaxbFactory = GWT.create(DrawingModuleJAXBParserFactory.class);
        JAXBParser<DrawingBean> jaxbParser = jaxbFactory.create();
        DrawingBean drawingBean = jaxbParser.parse(xml);
        assertEquals(drawingBean.getId(), ID);
        ImageBean imageBean = drawingBean.getImage();
        assertEquals(imageBean.getWidth(), WIDTH);
        assertEquals(imageBean.getHeight(), HEIGHT);
        assertEquals(imageBean.getSrc(), SRC);
        List<ColorBean> colorBeans = drawingBean.getPalette().getColors();
        int size = colorBeans.size();
        assertEquals(3, size);
        for (int x = 0; x < size; ++x) {
            ColorBean colorBean = colorBeans.get(x);
            assertEquals(colorBean.getDescription(), colorNames.get(x));
            assertEquals(colorBean.getRgb(), colors.get(x));
        }
    }
}
