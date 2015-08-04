package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class MuteMediaButton extends AbstractMediaButton {

    private final EventsBus eventsBus;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public MuteMediaButton(StyleNameConstants styleNames, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        super(styleNames.QP_MEDIA_MUTE());
        this.eventsBus = eventsBus;
        this.pageScopeFactory = pageScopeFactory;
    }

    @Override
    protected void onClick() {
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.MUTE, getMediaWrapper()), getMediaWrapper());
    }

    @Override
    public void init() {
        super.init();
        MediaEventHandler eventHandler = new MediaEventHandler() {
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
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_VOLUME_CHANGE), getMediaWrapper(), eventHandler, pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isMuteSupported();
    }
}
