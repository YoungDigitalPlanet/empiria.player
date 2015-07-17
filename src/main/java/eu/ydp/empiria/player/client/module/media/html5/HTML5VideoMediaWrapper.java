package eu.ydp.empiria.player.client.module.media.html5;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

import javax.annotation.PostConstruct;

public class HTML5VideoMediaWrapper extends AbstractHTML5MediaWrapper {

    private final HTML5MediaExecutorDelegator html5MediaExecutorDelegator = new HTML5MediaExecutorDelegator();

    @Inject
    private UserAgentUtil userAgentUtil;
    @Inject
    private PageScopeFactory pageScopeFactory;

    private final EventsBus eventsBus;

    @Inject
    public HTML5VideoMediaWrapper(@Assisted Media media, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        super(media, eventsBus, pageScopeFactory);
        this.eventsBus = eventsBus;
    }

    @PostConstruct
    public void registerEvents() {
        if (isHTML5VideoForcePosterNeeded()) {
            HTML5VideoForcePosterHack html5VideoForcePosterHack = new HTML5VideoForcePosterHack(getMediaBase(), html5MediaExecutorDelegator);
            CurrentPageScope currentPageScope = pageScopeFactory.getCurrentPageScope();
            addHandlerRegistration(MediaEventTypes.SUSPEND,
                    eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.SUSPEND), this, html5VideoForcePosterHack, currentPageScope));
            addHandlerRegistration(MediaEventTypes.ON_PLAY,
                    eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), this, html5VideoForcePosterHack, currentPageScope));
        }
    }

    private boolean isHTML5VideoForcePosterNeeded() {
        return (userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI) || userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI_WEBVIEW));
    }

    @Override
    public void setMediaExecutor(AbstractHTML5MediaExecutor mediaExecutor) {
        super.setMediaExecutor(mediaExecutor);
        html5MediaExecutorDelegator.setExecutor(mediaExecutor);
    }

}
