package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.Element;
import com.google.gwt.media.client.MediaBase;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.ConsoleLog;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

public abstract class AbstractPlayMediaButton extends AbstractMediaButton implements PlayerEventHandler {

    @Inject
    protected EventsBus eventsBus;
    @Inject
    private PageScopeFactory pageScopeFactory;
    private boolean shouldCreateNewWrapper;
    private MediaEventHandler handler;
    private CurrentPageScope scope;

    public AbstractPlayMediaButton(String baseStyleName) {
        super(baseStyleName);
    }

    protected abstract MediaEvent createMediaEvent();

    protected abstract boolean initButtonStyleChangeHandlersCondition();

    @Override
    public void init() {
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), this);
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
        handler = createButtonActivationHandler();
        scope = createCurrentPageScope();
        addMediaEventHandlers(handler, scope);
    }

    protected CurrentPageScope createCurrentPageScope() {
        return pageScopeFactory.getCurrentPageScope();
    }


    public void setMediaDescriptor(MediaWrapper<?> mw) {
        ConsoleLog.consoleLog(mw);
        if (mw != null) {
            MediaWrapper<MediaBase> mediaWrapper = (MediaWrapper<MediaBase>) mw;
            String src = mediaWrapper.getMediaObject().getElement().getFirstChildElement().getAttribute("src");
            mediaWrapperCreator.createMediaWrapper(src, new CallbackReceiver<MediaWrapper<?>>() {
                @Override
                public void setCallbackReturnObject(MediaWrapper<?> mw) {
                    handler = createButtonActivationHandler();
                    AbstractPlayMediaButton.super.setMediaDescriptor(mw);
                    addMediaEventHandlers(handler, scope);
                }
            });
        }
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

    @Inject
    private MediaWrapperCreator mediaWrapperCreator;


    @Override
    public void onPlayerEvent(PlayerEvent playerEvent) {

        MediaWrapper<MediaBase> mediaWrapper = (MediaWrapper<MediaBase>) super.getMediaWrapper();
        shouldCreateNewWrapper = true;

    }
}
