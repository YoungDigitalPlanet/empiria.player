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

package eu.ydp.empiria.player.client.module.img.template;

@SuppressWarnings("PMD")
public class Templates {
    public final static String SCREEN_ELEMENT_WITHOUT_CONTENT = "<mediaScreen id=\"mediaScreen\"></mediaScreen>";
    public final static String SCREEN_ELEMENT_WITH_CONTENT = "<mediaScreen id=\"mediaScreen\">"
            + "<simpleText id=\"dummy1_1_2\"> Klikaj głośniki i posłuchaj informacji. Staraj się jak najwięcej zapamiętać.</simpleText>"
            + "</mediaScreen>";

    public final static String EMPTY_TEMPLATE = "<img id=\"dummy1_2\" class=\"pict_f title\" src=\"media/ilustracja.jpg\"></img>";

    public final static String TEMPLATE_WITHOUT_FULLSCREEN_AND_CONTENT = "<img id=\"dummy1_2\" class=\"pict_f title\" src=\"media/ilustracja.jpg\">"
            + "<template>" + "<div class=\"pict_f\">" + "<mediaFullScreenButton id=\"mediaFullScreenButton\"></mediaFullScreenButton>"
            + "<mediaDescription id=\"mediaDescription\"></mediaDescription>" + "<mediaTitle id=\"mediaTitle\"></mediaTitle>"
            + "<mediaScreen id=\"mediaScreen\"></mediaScreen>" + "</div>" + "</template>" + "</img>";

    public final static String TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN = "<img id=\"dummy1_2\" class=\"pict_f title\" src=\"media/ilustracja.jpg\" srcFullScreen=\"media/ilustracja_f.jpg\">"
            + "<template>"
            + "<div class=\"pict_f\">"
            + "<mediaFullScreenButton id=\"mediaFullScreenButton\"></mediaFullScreenButton>"
            + "<mediaDescription id=\"mediaDescription\"></mediaDescription>"
            + "<mediaTitle id=\"mediaTitle\"></mediaTitle>"
            + "<mediaScreen id=\"mediaScreen\"></mediaScreen>" + "</div>" + "</template>" + "</img>";
    public final static String TEMPLATE_WITHOUT_CONTENT_WITH_DESCRIPTION = "<img id=\"dummy1_2\" class=\"pict_f title\" src=\"media/ilustracja.jpg\" srcFullScreen=\"media/ilustracja_f.jpg\">"
            + "<template>"
            + "<div class=\"pict_f\">"
            + "<mediaFullScreenButton id=\"mediaFullScreenButton\"></mediaFullScreenButton>"
            + "<mediaTitle id=\"mediaTitle\"></mediaTitle>" + "<mediaScreen id=\"mediaScreen\"></mediaScreen>" + "</div>" + "</template>" + "</img>";
    public final static String TEMPLATE_WITHOUT_CONTENT_WITH_LABEL = "<img id=\"dummy1_2\" class=\"pict_f title\" src=\"media/ilustracja.jpg\" srcFullScreen=\"media/ilustracja_f.jpg\">"
            + "<template>"
            + "<label/>"
            + "<div class=\"pict_f\">"
            + "<mediaFullScreenButton id=\"mediaFullScreenButton\"></mediaFullScreenButton>"
            + "<mediaDescription id=\"mediaDescription\"></mediaDescription>"
            + "<mediaTitle id=\"mediaTitle\"></mediaTitle>"
            + "<mediaScreen id=\"mediaScreen\"></mediaScreen>" + "</div>" + "</template>" + "</img>";
    public final static String TEMPLATE_WITH_CONTENT_WITHOUT_FULLSCREEN = "<img id=\"dummy1_2\" class=\"pict_f title\" src=\"media/ilustracja.jpg\">"
            + "<template>" + "<div class=\"pict_f\">" + "<mediaFullScreenButton id=\"mediaFullScreenButton\">"
            + "<simpleText id=\"dummy1_1_2\"> Klikaj głośniki i posłuchaj informacji. Staraj się jak najwięcej zapamiętać.</simpleText>"
            + "</mediaFullScreenButton>" + "<mediaDescription id=\"mediaDescription\">"
            + "<simpleText id=\"dummy1_1_2\"> Klikaj głośniki i posłuchaj informacji. Staraj się jak najwięcej zapamiętać.</simpleText>"
            + "</mediaDescription>" + "<mediaTitle id=\"mediaTitle\">"
            + "<simpleText id=\"dummy1_1_2\"> Klikaj głośniki i posłuchaj informacji. Staraj się jak najwięcej zapamiętać.</simpleText>"
            + "</mediaTitle>" + "<mediaScreen id=\"mediaScreen\">  </mediaScreen>" + "</div>" + "</template>" + "</img>";

    public final static String TEMPLATE_WITH_CONTENT_AND_FULLSCREEN = "<img id=\"dummy1_2\" class=\"pict_f title\" src=\"media/ilustracja.jpg\" srcFullScreen=\"media/ilustracja_f.jpg\">"
            + "<template>"
            + "<div class=\"pict_f\">"
            + "<mediaFullScreenButton id=\"mediaFullScreenButton\">"
            + "<simpleText id=\"dummy1_1_2\"> Klikaj głośniki i posłuchaj informacji. Staraj się jak najwięcej zapamiętać.</simpleText>"
            + "</mediaFullScreenButton>"
            + "<mediaDescription id=\"mediaDescription\">"
            + "<simpleText id=\"dummy1_1_2\"> Klikaj głośniki i posłuchaj informacji. Staraj się jak najwięcej zapamiętać.</simpleText>"
            + "</mediaDescription>"
            + "<mediaTitle id=\"mediaTitle\">"
            + "<simpleText id=\"dummy1_1_2\"> Klikaj głośniki i posłuchaj informacji. Staraj się jak najwięcej zapamiętać.</simpleText>"
            + "</mediaTitle>" + "<mediaScreen id=\"mediaScreen\">  </mediaScreen>" + "</div>" + "</template>" + "</img>";
}
