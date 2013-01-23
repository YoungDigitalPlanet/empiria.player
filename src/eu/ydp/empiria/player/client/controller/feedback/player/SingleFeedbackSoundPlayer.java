package eu.ydp.empiria.player.client.controller.feedback.player;

import javax.annotation.PostConstruct;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class SingleFeedbackSoundPlayer implements MediaEventHandler {

	@Inject
	protected EventsBus eventsBus;

	protected MediaWrapper<?> mediaWrapper;

	protected boolean played = false;
	protected boolean playAfterStop = false;

	@Inject
	public SingleFeedbackSoundPlayer(@Assisted MediaWrapper<?> mediaWrapper) {
		this.mediaWrapper = mediaWrapper;
	}

	@PostConstruct
	public void postConstruct() {
		eventsBus.addHandlerToSource(MediaEvent.getTypes(MediaEventTypes.ON_STOP, MediaEventTypes.ON_PLAY), mediaWrapper, this);
	}

	protected void firePlayEvent(final MediaWrapper<?> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, mediaWrapper), mediaWrapper);
	}

	protected void fireStopEvent(final MediaWrapper<?> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP, mediaWrapper), mediaWrapper);
	}

	public void play() {
		if (isPlayed()) {
			playAfterStop = true;
			fireStopEvent(mediaWrapper);
		} else {
			firePlayEvent(mediaWrapper);
		}
	}

	protected void playIfRequired(){
		if(playAfterStop){
			firePlayEvent(mediaWrapper);
			playAfterStop = false;
		}
	}

	protected boolean isPlayed() {
		return played;
	}
	public void setPlayed(boolean isPlayed) {
		this.played = isPlayed;
	}
	@Override
	public void onMediaEvent(MediaEvent event) {
		switch (event.getType()) {
		case ON_STOP:
			setPlayed(false);
			playIfRequired();
			break;
		case ON_PLAY:
			setPlayed(true);
			break;
		default:
			break;
		}
	}
}
