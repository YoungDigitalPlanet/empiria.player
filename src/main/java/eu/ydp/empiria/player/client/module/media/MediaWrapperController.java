package eu.ydp.empiria.player.client.module.media;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.*;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class MediaWrapperController {
	@Inject
	private EventsBus eventsBus;

	public void play(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(PLAY, mediaWrapper);
	}

	public void playLooped(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(PLAY_LOOPED, mediaWrapper);
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

	public void pauseAndPlay(MediaWrapper<Widget> mediaWrapper) {
		pause(mediaWrapper);
		play(mediaWrapper);
	}

	public void pauseAndPlayLooped(MediaWrapper<Widget> mediaWrapper) {
		pause(mediaWrapper);
		playLooped(mediaWrapper);
	}

	public void setCurrentTime(MediaWrapper<Widget> mediaWrapper, Double time) {
		MediaEvent event = new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, mediaWrapper);
		event.setCurrentTime(time);

		eventsBus.fireEventFromSource(event, mediaWrapper);
	}

	public double getCurrentTime(MediaWrapper<Widget> mediaWrapper) {
		return mediaWrapper.getCurrentTime();
	}

	public void addHandler(MediaEventTypes type, MediaWrapper<Widget> wrapper, MediaEventHandler handler) {
		eventsBus.addHandlerToSource(MediaEvent.getType(type), wrapper, handler);
	}

	private void fireEventFromSource(MediaEventTypes eventType, MediaWrapper<Widget> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(eventType, mediaWrapper), mediaWrapper);
	}
}
