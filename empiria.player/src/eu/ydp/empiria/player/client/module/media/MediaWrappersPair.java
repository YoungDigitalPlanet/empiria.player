package eu.ydp.empiria.player.client.module.media;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;

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
		// synchronizacja pomiedzy dwoma obiektami video
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_OPEN), fullScreanMediaWrapper, this, new CurrentPageScope());
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_EXIT), fullScreanMediaWrapper, this, new CurrentPageScope());

	}

	public MediaWrapper<?> getDefaultMediaWrapper() {
		return defaultMediaWrapper;
	}

	public MediaWrapper<?> getFullScreanMediaWrapper() {
		return fullScreanMediaWrapper;
	}

	
	private void fireSetCurrentTime(MediaWrapper<?> mediaWrapper,double time){
		MediaEvent event2 = new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, mediaWrapper);
		event2.setCurrentTime(time);
		eventsBus.fireAsyncEventFromSource(event2, mediaWrapper);

	}
	
	private void setCurrentTimeForMedia(final MediaWrapper<?> toSetMediaWrapper, final MediaWrapper<?> readFromMediaWrapper) {
		if (toSetMediaWrapper.getMediaObject() instanceof MediaBase) {
			MediaBase media = (MediaBase) toSetMediaWrapper.getMediaObject();
			if (media.getReadyState() != MediaElement.HAVE_NOTHING) {
				eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), toSetMediaWrapper, new MediaEventHandler() {
					@Override
					public void onMediaEvent(MediaEvent event) {
						fireSetCurrentTime(toSetMediaWrapper, readFromMediaWrapper.getCurrentTime());
					}
				}, new CurrentPageScope());
				MediaEvent event2 = new MediaEvent(MediaEventTypes.PLAY, toSetMediaWrapper);
				eventsBus.fireAsyncEventFromSource(event2, toSetMediaWrapper);
			}else{
				fireSetCurrentTime(toSetMediaWrapper, readFromMediaWrapper.getCurrentTime());
			}
		} else {
			fireSetCurrentTime(toSetMediaWrapper, readFromMediaWrapper.getCurrentTime());
		}
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		// synchronizujemy sciezki
		if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_OPEN) {
			setCurrentTimeForMedia(fullScreanMediaWrapper, defaultMediaWrapper);
		} else if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_EXIT) {
			setCurrentTimeForMedia(defaultMediaWrapper, fullScreanMediaWrapper);
		}
	}
}
