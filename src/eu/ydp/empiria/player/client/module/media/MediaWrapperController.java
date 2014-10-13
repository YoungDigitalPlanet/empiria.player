package eu.ydp.empiria.player.client.module.media;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.*;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class MediaWrapperController {
	@Inject
	private EventsBus eventsBus;

	public void play(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(PLAY, mediaWrapper);
	}

	public void stop(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(STOP, mediaWrapper);
	}

	public void pause(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(PAUSE, mediaWrapper);
	}

	public void resume(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(RESUME, mediaWrapper);
	}

	public void stopAndPlay(MediaWrapper<Widget> mediaWrapper) {
		stop(mediaWrapper);
		play(mediaWrapper);
	}

	public void addHandler(MediaEventTypes type, MediaWrapper<Widget> wrapper, MediaEventHandler handler) {
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), wrapper, handler);
	}

	private void fireEventFromSource(MediaEventTypes eventType, MediaWrapper<Widget> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(eventType, mediaWrapper), mediaWrapper);
	}
}
