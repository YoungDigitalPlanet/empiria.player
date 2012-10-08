package eu.ydp.empiria.player.client.module.object.impl;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class HTML5VideoAndroid34Impl extends HTML5VideoAndroidImpl {
	private boolean videoReady = false;

	@Override
	public void setEventBusSourceObject(MediaWrapper<?> object) {
		super.setEventBusSourceObject(object);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getEventBusSourceObject(), new MediaEventHandler() {
			int stopTime = 300; // zatrzymujemy 300ms przed koncem niestety na

			// galaxytabie ponizej nie zalapie
			@Override
			public void onMediaEvent(MediaEvent event) {
				// 300ms przed koncem filmu
				if (videoReady) {
					double currentTime = video.getCurrentTime() * 1000;
					double duration = video.getDuration() * 1000;

					if (currentTime >= 1000 && currentTime > duration - stopTime) {
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
