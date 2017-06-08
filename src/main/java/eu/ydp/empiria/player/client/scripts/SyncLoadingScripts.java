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

package eu.ydp.empiria.player.client.scripts;

import eu.ydp.gwtutil.client.scripts.ScriptUrl;

public enum SyncLoadingScripts implements ScriptUrl {

    JQUERY("jquery/jquery-1.11.3.min.js"),
    CSS_PARSER("jscss/cssparser.js"),
    JQUERY_UI("jquery/jquery-ui.min.js"),
    JQUERY_UI_TOUCH("jquery/jquery.ui.touch-punch.min.js"),
    VIDEO_AC("video/AC_RunActiveContent.js"),
    FA_VIDEO("video/FAVideo.js"),
    VIDEO_JS("video-js/video.js"),
    JQUERY_TE("jqueryte/jquery-te-1.4.0.min.js"),
    JQUERY_SCROLL("jquery/jquery.smooth-scroll.min.js"),
    LIGHTBOX("lightbox2/js/lightbox-min.js"),
    MAGNIFIC_POPUP("magnific-popup/magnific-popup.min.js");

    private final String url;

    SyncLoadingScripts(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

}
