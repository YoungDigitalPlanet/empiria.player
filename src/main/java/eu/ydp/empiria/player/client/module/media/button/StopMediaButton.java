package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

/**
 * przycisk stop
 *
 * @author plelakowski
 */
public class StopMediaButton extends AbstractMediaButton {
    @Inject
    private EventsBus eventsBus;

    @Inject
    public StopMediaButton(EventsBus eventsBus) {
        super("qp-media-stop", false);
        this.eventsBus = eventsBus;
    }

    @Override
    protected void onClick() {
        changeStyleForClick();
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP, getMediaWrapper()), getMediaWrapper());
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isStopSupported();
    }
}
