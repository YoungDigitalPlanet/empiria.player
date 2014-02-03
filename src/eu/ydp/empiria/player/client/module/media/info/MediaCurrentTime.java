package eu.ydp.empiria.player.client.module.media.info;

import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * Widget wyswietlajacy pozycje w skaznika w pliku w postaci czasu. Dokladnosc 1 sekunda
 * 
 */
public class MediaCurrentTime extends AbstractMediaTime<MediaCurrentTime> {
	public MediaCurrentTime() {
		super(styleNames.QP_MEDIA_CURRENTTIME());
	}

	@Override
	public MediaCurrentTime getNewInstance() {
		return new MediaCurrentTime();
	}

	@Override
	public void init() {
		AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
			// -1 aby przy pierwszym zdarzeniu pokazal sie timer
			int lastTime = -1;

			@Override
			public void onMediaEvent(MediaEvent event) {
				double currentTime = getMediaWrapper().getCurrentTime();
				if (currentTime > lastTime + 1 || currentTime < lastTime - 1) {
					lastTime = (int) currentTime;
					double timeModulo = currentTime % 60;
					getElement().setInnerText(getInnerText((currentTime - timeModulo) / 60f, timeModulo));
				}
			}
		};
		if (isSupported()) {
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), handler, new CurrentPageScope());
		}
	}
}
