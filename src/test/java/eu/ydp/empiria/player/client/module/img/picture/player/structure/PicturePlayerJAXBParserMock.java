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

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

public class PicturePlayerJAXBParserMock implements PicturePlayerJAXBParser {

    public static String FULL_PICTURE_PLAYER = "<img id=\"dummy1_2_playerPicture\" src=\"src.jpg\" alt=\"alt\" srcFullScreen=\"src_f.jpg\" fullscreenMode=\"mode\">\n"
            + "\t<title>title</title>\n"
            + "</img>\t";

    public static String PICTURE_PLAYER_WITHOUT_TITLE =
            "<img id=\"dummy1_2_playerPicture\" src=\"src.jpg\" alt=\"alt\" srcFullScreen=\"src_f.jpg\" fullscreenMode=\"mode\">\n"
                    + "</img>\t";

    public static String PICTURE_PLAYER_WITHOUT_FULLSCREENMODE = "<img id=\"dummy1_2_playerPicture\" src=\"src.jpg\" alt=\"alt\" srcFullScreen=\"src_f.jpg\">\n "
            + "\t<title>title</title>\n"
            + "</img>\t";

    public static String PICTURE_PLAYER_WITHOUT_SRC_FULLSCREEN = "<img id=\"dummy1_2_playerPicture\" alt=\"alt\" src=\"src.jpg\">\n "
            + "\t<title>title</title>\n"
            + "</img>\t";

    public static String PICTURE_PLAYER_WITHOUT_ALT = "<img id=\"dummy1_2_playerPicture\" src=\"src.jpg\" srcFullScreen=\"src_f.jpg\" fullscreenMode=\"mode\">\n"
            + "\t<title>title</title>\n"
            + "</img>\t";

    @Override
    public JAXBParser<PicturePlayerBean> create() {
        return new JAXBParserImpl<>(PicturePlayerJAXBParser.class);
    }
}
