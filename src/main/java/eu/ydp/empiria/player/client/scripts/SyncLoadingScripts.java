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
