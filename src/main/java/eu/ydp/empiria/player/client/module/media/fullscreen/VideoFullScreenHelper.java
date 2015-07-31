package eu.ydp.empiria.player.client.module.media.fullscreen;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.gin.factory.MediaFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.fullscreen.VideoFullScreenEvent;
import eu.ydp.empiria.player.client.util.events.internal.fullscreen.VideoFullScreenEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;

import javax.annotation.PostConstruct;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent.FIREFOX;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE8;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE9;

public class VideoFullScreenHelper implements KeyUpHandler, VideoFullScreenEventHandler {

    @Inject
    protected EventsBus eventsBus;
    @Inject
    protected ObjectTemplateParser parser;
    @Inject
    protected StyleNameConstants styleNames;
    @Inject
    protected PageScopeFactory pageScopeFactory;
    @Inject
    private NativeHTML5FullScreenHelper html5FullScreenHelper;
    @Inject
    private Provider<VideoFullScreenView> fullScreenViewProvider;
    @Inject
    private MediaFactory mediaFactory;

    protected VideoFullScreenView view;
    protected VideoControlHideTimer controlsHideTimer;
    protected MediaWrapper<?> lastMediaWrapper = null;
    protected MediaWrapper<?> synchronizeWithMediaWrapper = null;
    private HandlerRegistration onDataReadyPlayHndlerRegistration = null;
    private boolean playEventNeeded = true;

    @PostConstruct
    public void postConstruct() {
        html5FullScreenHelper.addFullScreenEventHandler(this);
    }

    /**
     * Powiadamia listentery
     *
     * @param inFullScreen czy jest aktywny fullscreen
     */
    private void fireEvent(final boolean inFullScreen, final MediaWrapper<?> mediaWrapper) {
        if (inFullScreen) {
            eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_OPEN), mediaWrapper, pageScopeFactory.getCurrentPageScope());
        } else {
            eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_EXIT), mediaWrapper, pageScopeFactory.getCurrentPageScope());
        }
    }

    private void firePlayEvent(final MediaWrapper<?> mediaWrapper) {
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, mediaWrapper), mediaWrapper, pageScopeFactory.getCurrentPageScope());
    }

    protected FlowPanel parseTemplate(MediaWrapper<?> mediaWrapper, Element template, FlowPanel parent) {
        parser.setMediaWrapper(mediaWrapper);
        parser.setFullScreenMediaWrapper(mediaWrapper);
        parser.setFullScreen(true);
        parser.parse(template, parent);
        return parent;
    }

    protected void resizeToFullScreen(Widget widget, Position position) {
        Style style = widget.getElement().getStyle();
        style.setPosition(position);
        style.setTop(0, Unit.PX);
        style.setLeft(0, Unit.PX);
        style.setWidth(100, Unit.PCT);
        style.setHeight(100, Unit.PCT);
    }

    private VideoFullScreenView getFullScreenView() {
        if (view == null) {
            view = fullScreenViewProvider.get();
            controlsHideTimer = mediaFactory.getVideoControlHideTimer(view);
            RootPanel.get().addDomHandler(this, KeyUpEvent.getType());
        }
        return view;
    }

    protected void clearFullScreenView() {
        if (view != null && view.isAttached()) {
            view.removeFromParent();
            view.getContainer().clear();
            view.getControls().clear();
            view.getContainer().add(view.getControls());
        }
    }

    protected void openFullScreenMobile(MediaWrapper<?> mediaWrapper) {
        if (isHTML5VideoMediaWrapper(mediaWrapper) && !isHTML5MediaDataAvaliable(mediaWrapper)) {
            playHTML5MediaAfterDataLoad(mediaWrapper);
        } else {
            openFullScreenMobileWhenDataReady(mediaWrapper);
        }
    }

    private void playHTML5MediaAfterDataLoad(final MediaWrapper<?> mediaWrapper) {
        ((MediaBase) mediaWrapper.getMediaObject()).play();
        onDataReadyPlayHndlerRegistration = eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), mediaWrapper, new MediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                playEventNeeded = false;
                openFullScreenMobileWhenDataReady(mediaWrapper);
                onDataReadyPlayHndlerRegistration.removeHandler();
            }
        });
    }

    private boolean isHTML5MediaDataAvaliable(MediaWrapper<?> mediaWrapper) {
        return isHTML5VideoMediaWrapper(mediaWrapper) && (mediaWrapper).canPlay();
    }

    private boolean isHTML5VideoMediaWrapper(MediaWrapper<?> mediaWrapper) {
        return mediaWrapper instanceof HTML5VideoMediaWrapper;
    }

    private void openFullScreenMobileWhenDataReady(MediaWrapper<?> mediaWrapper) {
        html5FullScreenHelper.requestFullScreen((mediaWrapper.getMediaObject()).getElement());
        lastMediaWrapper = mediaWrapper;
        fireEvent(true, mediaWrapper);
        eventsBus.fireEvent(new MediaEvent(MediaEventTypes.ON_MOBILE_FULL_SCREEN_OPEN));
    }

    protected void openFullScreenDesktop(MediaWrapper<?> mediaWrapper, Element template) {
        if (mediaWrapper != null && template != null) {
            clearFullScreenView();
            lastMediaWrapper = mediaWrapper;
            VideoFullScreenView parent = getFullScreenView();
            parseTemplate(mediaWrapper, template, parent.getControls());
            fireEvent(true, mediaWrapper);
            RootPanel.get().add(parent);
            html5FullScreenHelper.requestFullScreen(parent.getElement());
            resizeToFullScreen(lastMediaWrapper.getMediaObject(), Position.FIXED);
        }
    }

    protected void openFullscreenIE(MediaWrapper<?> mediaWrapper, Element template) {
        if (mediaWrapper != null && template != null) {
            clearFullScreenView();
            lastMediaWrapper = mediaWrapper;
            VideoFullScreenView parent = getFullScreenView();
            Widget widget = mediaWrapper.getMediaObject();
            parent.getContainer().add(widget);
            resizeToFullScreen(parent.asWidget(), Position.FIXED);
            resizeToFullScreen(widget, Position.ABSOLUTE);
            parseTemplate(mediaWrapper, template, parent.getControls());
            fireEvent(true, mediaWrapper);
            RootPanel.get().add(parent);
            resizeToFullScreen(lastMediaWrapper.getMediaObject(), Position.FIXED);
            resizeToFullScreen(view.asWidget(), Position.FIXED);
            resizeToFullScreen(widget, Position.ABSOLUTE);
        }
    }

    public void openFullScreen(MediaWrapper<?> fullScreenMediaWrapper, MediaWrapper<?> defaultMediaWrapper, Element template) {
        playEventNeeded = true;
        synchronizeWithMediaWrapper = defaultMediaWrapper;

        boolean isMobileUserAgent = UserAgentChecker.isMobileUserAgent();
        boolean isSafari = UserAgentChecker.isUserAgent(UserAgent.SAFARI);
        boolean isMobileFirefox = UserAgentChecker.isMobileUserAgent(FIREFOX);

        if ((isMobileUserAgent || isSafari) && !isMobileFirefox) {
            openFullScreenMobile(defaultMediaWrapper);
        } else if (UserAgentChecker.isUserAgent(IE8, IE9, FIREFOX)) {
            openFullscreenIE(fullScreenMediaWrapper, template);
        } else {
            openFullScreenDesktop(fullScreenMediaWrapper, template);
        }
    }

    public void closeFullScreen() {
        html5FullScreenHelper.exitFullScreen();
        clearFullScreenView();
        fireEvent(false, lastMediaWrapper);
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
            event.preventDefault();
            closeFullScreen();
        }

    }

    @Override
    public void handleEvent(VideoFullScreenEvent event) {
        if (event.isInFullScreen() && playEventNeeded) {
            firePlayEvent(lastMediaWrapper);
        } else {
            closeFullScreen();

        }
    }
}
