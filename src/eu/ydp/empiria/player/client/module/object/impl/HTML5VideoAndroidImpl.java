package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.Video;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;

/**
 * domyslna implementacja dla androida
 *
 */
public class HTML5VideoAndroidImpl extends HTML5VideoImpl {
	public class MP4ErrorHandler implements HTML5MediaEventHandler {
		private final Video video;

		public MP4ErrorHandler(Video video) {
			this.video = video;
		}

		// dla androida 2.3.x, 4.x obejscie problemow gdy mp4 jest uszkodzony i
		// player sie zawiesza
		@Override
		public void onEvent(HTML5MediaEvent html5Event) {
			video.load();
		}
	}

	public HTML5VideoAndroidImpl() {
		super();
		video.addBitlessDomHandler(new MP4ErrorHandler(video), HTML5MediaEvent.getType(HTML5MediaEventsType.ended));
	}
}
