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

package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.jaxb.XmlContentMock;

public class PicturePlayerJAXBParserGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private PicturePlayerBean picturePlayerExpected = new PicturePlayerBean();
    private PicturePlayerTitleBean titleExpected = new PicturePlayerTitleBean();

    public PicturePlayerJAXBParserGWTTestCase() {
        titleExpected.setTitleName(new XmlContentMock("title"));

        picturePlayerExpected.setFullscreenMode("mode");
        picturePlayerExpected.setSrcFullScreen("src_f.jpg");
        picturePlayerExpected.setSrc("src.jpg");
        picturePlayerExpected.setAlt("alt");
        picturePlayerExpected.setTitleBean(titleExpected);
    }

    public void testFullPicturePlayer() {
        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.FULL_PICTURE_PLAYER);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);
    }

    public void testPicturePlayer_withoutTitle() {
        // given
        picturePlayerExpected.setTitleBean(null);

        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.PICTURE_PLAYER_WITHOUT_TITLE);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);

    }

    public void testPicturePlayer_withoutFullscreenMode() {
        // given
        picturePlayerExpected.setFullscreenMode(null);

        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.PICTURE_PLAYER_WITHOUT_FULLSCREENMODE);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);
    }

    public void testPicturePlayer_withoutFullscreen() {
        // given
        picturePlayerExpected.setFullscreenMode(null);
        picturePlayerExpected.setSrcFullScreen(null);

        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.PICTURE_PLAYER_WITHOUT_SRC_FULLSCREEN);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);

    }

    public void testPicturePlayer_withoutAlt() {
        // given
        picturePlayerExpected.setAlt(null);

        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.PICTURE_PLAYER_WITHOUT_ALT);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);
    }

    private void assertPicturePlayerEquals(PicturePlayerBean expected, PicturePlayerBean result) {
        assertEquals(expected.getFullscreenMode(), result.getFullscreenMode());
        assertEquals(expected.getSrcFullScreen(), result.getSrcFullScreen());
        assertEquals(expected.getSrc(), result.getSrc());
        if (result.hasTitle()) {
            assertTitleEquals(expected.getTitleBean(), result.getTitleBean());
        }
        if (result.hasAlt()) {
            assertEquals(expected.getAlt(), result.getAlt());
        }
    }

    private void assertTitleEquals(PicturePlayerTitleBean expected, PicturePlayerTitleBean result) {
        assertEquals(expected.getTitleName().toString(), result.getTitleName().getValue().getChildNodes().toString());
    }

    private PicturePlayerBean parse(String xml) {
        PicturePlayerJAXBParser jaxbParserFactory = GWT.create(PicturePlayerJAXBParser.class);
        JAXBParser<PicturePlayerBean> jaxbParser = jaxbParserFactory.create();
        PicturePlayerBean picturePlayerBean = jaxbParser.parse(xml);
        return picturePlayerBean;
    }
}
