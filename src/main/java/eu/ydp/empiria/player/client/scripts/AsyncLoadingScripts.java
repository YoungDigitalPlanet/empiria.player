package eu.ydp.empiria.player.client.scripts;

import eu.ydp.gwtutil.client.scripts.ScriptUrl;

enum AsyncLoadingScripts implements ScriptUrl {

	JQUERY_TE("jqueryte/jquery-te-1.4.0.min.js"),
	VIDEO_JS("video-js/video.dev.js"),
	VIDEO_AC("video/AC_RunActiveContent.js"),
	FA_VIDEO("video/FAVideo.js"),
	JQUERY_SCROLL("jquery/jquery.smooth-scroll.min.js"),
	LIGHTBOX("lightbox2/js/lightbox-min.js"),
	MAGNIFIC_POPUP("magnific-popup/magnific-popup.min.js");

	private final String url;


	AsyncLoadingScripts(String url) {
		this.url = url;
	}

	@Override
	public String getUrl() {
		return url;
	}

}
