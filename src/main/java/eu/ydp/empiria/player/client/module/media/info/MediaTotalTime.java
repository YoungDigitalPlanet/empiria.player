package eu.ydp.empiria.player.client.module.media.info;

import eu.ydp.empiria.player.client.util.events.internal.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

/**
 * Widget prezentujacy dlugosc pliku audio lub video
 * 
 */
public class MediaTotalTime extends AbstractMediaTime<MediaTotalTime> {
	public MediaTotalTime() {
		super(styleNames.QP_MEDIA_TOTALTIME());
	}

	@Override
	public MediaTotalTime getNewInstance() {
		return new MediaTotalTime();
	}

	@Override
	public void init() {
		AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				double duration = getMediaWrapper().getDuration();
				double timeModulo = duration % 60;
				getElement().setInnerText(getInnerText((duration - timeModulo) / 60f, timeModulo));
			}
		};
		if (isSupported()) {
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler, new CurrentPageScope());
		}
	}
}
