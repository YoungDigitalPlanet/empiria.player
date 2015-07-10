package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

/**
 * Przycisk mute
 *
 * @author plelakowski
 */
public class MuteMediaButton extends AbstractMediaButton {

    private final EventsBus eventsBus;

    @Inject
    public MuteMediaButton(StyleNameConstants styleNames, EventsBus eventsBus) {
        super(styleNames.QP_MEDIA_MUTE());
        this.eventsBus = eventsBus;
    }

    @Override
    protected void onClick() {
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.MUTE, getMediaWrapper()), getMediaWrapper());
    }

    @Override
    public void init() {
        super.init();
        AbstractMediaEventHandler eventHandler = new AbstractMediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                if (event.getMediaWrapper().isMuted()) {
                    setActive(true);
                } else {
                    setActive(false);
                }
                changeStyleForClick();
            }
        };
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_VOLUME_CHANGE), getMediaWrapper(), eventHandler, new CurrentPageScope());
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isMuteSupported();
    }
}
