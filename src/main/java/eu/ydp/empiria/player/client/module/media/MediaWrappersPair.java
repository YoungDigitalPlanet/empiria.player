package eu.ydp.empiria.player.client.module.media;

import com.google.gwt.media.client.MediaBase;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent.FIREFOX;

public class MediaWrappersPair implements MediaEventHandler {
    @Inject
    protected EventsBus eventsBus;
    @Inject
    protected PageScopeFactory pageScopeFactory;
    protected MediaWrapper<?> defaultMediaWrapper;
    protected MediaWrapper<?> fullScreanMediaWrapper;
    protected HandlerRegistration onPlayHandlerRegistration;
    List<HandlerRegistration> handlersRegistration = new ArrayList<HandlerRegistration>();

    @Inject
    public MediaWrappersPair(@Assisted("default") MediaWrapper<?> defaultMediaWrapper, @Assisted("fullscreen") MediaWrapper<?> fullScreanMediaWrapper) {
        this.defaultMediaWrapper = defaultMediaWrapper;
        this.fullScreanMediaWrapper = fullScreanMediaWrapper;

    }

    @PostConstruct
    public void postConstruct() {
        if (isSynchronizationNeeded()) { // powoduje problemy na
            // mobilnych
            // synchronizacja pomiedzy dwoma obiektami video
            HandlerRegistration addHandlerToSource = eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_OPEN),
                    fullScreanMediaWrapper, this, pageScopeFactory.getCurrentPageScope());
            handlersRegistration.add(addHandlerToSource);
            addHandlerToSource = eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_EXIT), fullScreanMediaWrapper, this,
                    pageScopeFactory.getCurrentPageScope());
            handlersRegistration.add(addHandlerToSource);
        }
    }

    private boolean isSynchronizationNeeded() {
        boolean isSynchronizationNeeded = true;

        if (isMobileButNotFirefox()) {
            isSynchronizationNeeded = false;
        } else if (UserAgentChecker.isUserAgent(UserAgent.SAFARI)) {
            isSynchronizationNeeded = false;
        }

        return isSynchronizationNeeded;
    }

    private boolean isMobileButNotFirefox() {
        return UserAgentChecker.isMobileUserAgent() && !UserAgentChecker.isMobileUserAgent(FIREFOX);
    }

    public MediaWrapper<?> getDefaultMediaWrapper() {
        return defaultMediaWrapper;
    }

    public MediaWrapper<?> getFullScreanMediaWrapper() {
        return fullScreanMediaWrapper;
    }

    private void fireSetCurrentTime(MediaWrapper<?> mediaWrapper, double time) {
        MediaEvent event = new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, mediaWrapper);
        event.setCurrentTime(time);
        eventsBus.fireAsyncEventFromSource(event, mediaWrapper);
    }

    protected void firePlay(MediaWrapper<?> mediaWrapper) {
        MediaEvent event = new MediaEvent(MediaEventTypes.PLAY, mediaWrapper);
        eventsBus.fireAsyncEventFromSource(event, mediaWrapper);
    }

    protected void setCurrentTimeForMedia(final MediaWrapper<?> toSetMediaWrapper, final MediaWrapper<?> readFromMediaWrapper) {
        if (toSetMediaWrapper.getMediaObject() instanceof MediaBase) {
            eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PAUSE, readFromMediaWrapper), readFromMediaWrapper);
            eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PAUSE, toSetMediaWrapper), toSetMediaWrapper);
            onPlayHandlerRegistration = eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), toSetMediaWrapper, new MediaEventHandler() {
                @Override
                public void onMediaEvent(MediaEvent event) {
                    onPlayHandlerRegistration.removeHandler();
                    fireSetCurrentTime(toSetMediaWrapper, readFromMediaWrapper.getCurrentTime());
                }
            }, pageScopeFactory.getCurrentPageScope());
            firePlay(toSetMediaWrapper);
        } else {
            fireSetCurrentTime(toSetMediaWrapper, readFromMediaWrapper.getCurrentTime());
        }
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        // synchronizujemy sciezki
        if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_OPEN) {
            setCurrentTimeForMedia(fullScreanMediaWrapper, defaultMediaWrapper);
            Video mediaObject = (Video) defaultMediaWrapper.getMediaObject();
            Video fullScreenMediaObject = (Video) fullScreanMediaWrapper.getMediaObject();
            fullScreenMediaObject.setPoster(mediaObject.getPoster());
        } else if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_EXIT) {
            setCurrentTimeForMedia(defaultMediaWrapper, fullScreanMediaWrapper);
        }
    }

    public void disableFullScreenSynchronization() {
        for (HandlerRegistration reg : handlersRegistration) {
            reg.removeHandler();
        }
        handlersRegistration.clear();
    }
}
