package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.Video;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * domyslna implementacja dla androida
 * 
 */
public class HTML5VideoAndroidImpl extends HTML5VideoImpl {
	public class MP4ErrorHandler implements MediaEventHandler {
		private final Video video;

		public MP4ErrorHandler(Video video) {
			this.video = video;
		}

		// dla androida 2.3.x, 4.x obejscie problemow gdy mp4 jest uszkodzony i
		// player sie zawiesza
		@Override
		public void onMediaEvent(MediaEvent event) {
			video.load();

		}
	}

	@Override
	public void setEventBusSourceObject(MediaWrapper<?> object) {
		super.setEventBusSourceObject(object);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getEventBusSourceObject(), new MP4ErrorHandler(video), new CurrentPageScope());
	}
}
