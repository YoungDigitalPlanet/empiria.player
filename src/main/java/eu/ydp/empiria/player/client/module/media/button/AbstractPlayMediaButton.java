package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

public abstract class AbstractPlayMediaButton extends AbstractMediaButton {

    @Inject
    protected EventsBus eventsBus;
    @Inject
    private PageScopeFactory pageScopeFactory;

    public AbstractPlayMediaButton(String baseStyleName) {
        super(baseStyleName);
    }

    protected abstract MediaEvent createMediaEvent();

    protected abstract boolean initButtonStyleChangeHandlersCondition();

    @Override
    public void init() {
        super.init();
        if (initButtonStyleChangeHandlersCondition()) {
            initButtonStyleChangeHandlers();
        }
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isPlaySupported();
    }

    protected void initButtonStyleChangeHandlers() {
        MediaEventHandler handler = createButtonActivationHandler();
        CurrentPageScope scope = createCurrentPageScope();
        addMediaEventHandlers(handler, scope);
    }

    protected CurrentPageScope createCurrentPageScope() {
        return pageScopeFactory.getCurrentPageScope();
    }

    @Override
    protected void onClick() {
        MediaEvent mediaEvent = createMediaEvent();
        eventsBus.fireEventFromSource(mediaEvent, getMediaWrapper());
    }

    private void addMediaEventHandlers(MediaEventHandler handler, CurrentPageScope scope) {
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), getMediaWrapper(), handler, scope);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getMediaWrapper(), handler, scope);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), getMediaWrapper(), handler, scope);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), getMediaWrapper(), handler, scope);
    }

    private MediaEventHandler createButtonActivationHandler() {
        return new MediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                if (event.getType() == MediaEventTypes.ON_PLAY) {
                    setActive(true);
                } else {
                    setActive(false);
                }
                changeStyleForClick();
            }
        };
    }
}