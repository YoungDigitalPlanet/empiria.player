package eu.ydp.empiria.player.client.module.object.impl;

import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class HTML5VideoAndroid3Impl extends HTML5VideoAndroidImpl {
	private boolean videoReady = false;

	public HTML5VideoAndroid3Impl() {
		super();
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getEventBusSourceObject(), new MediaEventHandler() {
			int stopTime = 300; // zatrzymujemy 300ms przed koncem niestety na

			// galaxytabie ponizej nie zalapie
			@Override
			public void onMediaEvent(MediaEvent event) {
				// 300ms przed koncem filmu
				if (videoReady) {
					double currentTime = video.getCurrentTime();
					double duration = video.getDuration();
					if (currentTime >= 1000 && currentTime * 1000 > duration * 1000 - stopTime) {
						video.pause();
						video.setCurrentTime(0);
					}
				}

			}
		}, new CurrentPageScope());

		// czekamy na informacje na temat dlugosci utworu
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getEventBusSourceObject(), new MediaEventHandler() {

			@Override
			public void onMediaEvent(MediaEvent event) {
				videoReady = true;
			}
		}, new CurrentPageScope());

	}
}
