package eu.ydp.empiria.player.client.module.media.info;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.progress.ProgressUpdateLogic;
import eu.ydp.empiria.player.client.util.events.internal.media.*;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

/**
 * Widget wyswietlajacy pozycje w skaznika w pliku w postaci czasu. Dokladnosc 1
 * sekunda
 * 
 */
public class MediaCurrentTime extends AbstractMediaTime<MediaCurrentTime> {

	@Inject
	private ProgressUpdateLogic progressUpdateLogic;

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
				if (progressUpdateLogic.isReadyToUpdate(currentTime, lastTime)) {
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
