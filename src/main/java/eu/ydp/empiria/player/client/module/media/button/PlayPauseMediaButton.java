package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class PlayPauseMediaButton extends AbstractPlayMediaButton {

    @Inject
    public PlayPauseMediaButton(StyleNameConstants styleNames) {
        super(styleNames.QP_MEDIA_PLAY_PAUSE());
    }

    @Override
    protected boolean initButtonStyleChangeHandlersCondition() {
        return getMediaAvailableOptions().isPauseSupported();
    }

    @Override
    protected MediaEvent createMediaEvent() {
        MediaEvent mediaEvent;
        if (isActive()) {
            mediaEvent = new MediaEvent(MediaEventTypes.PAUSE, getMediaWrapper());
        } else {
            mediaEvent = new MediaEvent(MediaEventTypes.PLAY, getMediaWrapper());
        }
        return mediaEvent;
    }
}
