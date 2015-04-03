package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class PlayStopMediaButton extends AbstractPlayMediaButton<PlayStopMediaButton> {

	@Inject
	public PlayStopMediaButton(StyleNameConstants styleNames) {
		super(styleNames.QP_MEDIA_PLAY_STOP());
	}

	@Override
	protected boolean initButtonStyleChangeHandlersCondition() {
		return getMediaAvailableOptions().isStopSupported();
	}

	@Override
	protected MediaEvent createMediaEvent() {
		MediaEvent mediaEvent;
		if (isActive()) {
			mediaEvent = new MediaEvent(MediaEventTypes.STOP, getMediaWrapper());
		} else {
			mediaEvent = new MediaEvent(MediaEventTypes.PLAY, getMediaWrapper());
		}
		return mediaEvent;
	}
}