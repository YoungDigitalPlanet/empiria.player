package eu.ydp.empiria.player.client.scripts;

enum ScriptsList {
	// after YPUB-6716, excanvas should be deleted
	EXCANVAS("excanvas.js"), VIDEO_JS("video-js/video.dev.js"), VIDEO_AC("video/AC_RunActiveContent.js"), FA_VIDEO("video/FAVideo.js"), CSS_PARSER(
			"jscss/cssparser.js"), JQUERY_TE("jqueryte/jquery-te-1.4.0.min.js"), JQUERY_UI("jquery/jquery-ui-1.10.3.custom.min.js"), JQUERY_UI_TOUCH(
			"jquery/jquery.ui.touch-punch.min.js"), JQUERY_SCROLL("jquery/jquery.smooth-scroll.min.js"), LIGHTBOX("lightbox2/js/lightbox-min.js");

	private final String url;

	private ScriptsList(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
