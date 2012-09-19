package eu.ydp.empiria.player.client.module.media;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class MediaWrappersPair implements MediaEventHandler {
	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	protected MediaWrapper<?> defaultMediaWrapper;
	protected MediaWrapper<?> fullScreanMediaWrapper;

	public MediaWrappersPair(MediaWrapper<?> defaultMediaWrapper, MediaWrapper<?> fullScreanMediaWrapper) {
		super();
		this.defaultMediaWrapper = defaultMediaWrapper;
		this.fullScreanMediaWrapper = fullScreanMediaWrapper;
		//synchronizacja pomiedzy dwoma obiektami video
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_OPEN), fullScreanMediaWrapper, this, new CurrentPageScope());
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_EXIT), fullScreanMediaWrapper, this, new CurrentPageScope());
	}

	public MediaWrapper<?> getDefaultMediaWrapper() {
		return defaultMediaWrapper;
	}

	public MediaWrapper<?> getFullScreanMediaWrapper() {
		return fullScreanMediaWrapper;
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		//synchronizujemy sciezki
		if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_OPEN) {
			MediaEvent event2 = new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, fullScreanMediaWrapper);
			event2.setCurrentTime(defaultMediaWrapper.getCurrentTime());
			eventsBus.fireAsyncEventFromSource(event2, fullScreanMediaWrapper);
		}else if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_EXIT) {
			MediaEvent event2 = new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, defaultMediaWrapper);
			event2.setCurrentTime(fullScreanMediaWrapper.getCurrentTime());
			eventsBus.fireAsyncEventFromSource(event2, defaultMediaWrapper);
		}
	}
}
