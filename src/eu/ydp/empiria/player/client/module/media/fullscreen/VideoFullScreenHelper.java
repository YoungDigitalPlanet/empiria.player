package eu.ydp.empiria.player.client.module.media.fullscreen;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent.FIREFOX;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE8;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE9;

import javax.annotation.PostConstruct;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.HTML5FullScreenHelper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.fullscreen.VideoFullScreenEvent;
import eu.ydp.empiria.player.client.util.events.fullscreen.VideoFullScreenEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class VideoFullScreenHelper implements KeyUpHandler, VideoFullScreenEventHandler {

	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected ObjectTemplateParser<?> parser;

	@Inject
	protected GWTPanelFactory panelFactory;

	@Inject
	protected StyleNameConstants styleNames;

	@Inject
	protected PageScopeFactory pageScopeFactory;

	@Inject
	private HTML5FullScreenHelper html5FullScreenHelper;

	protected FlowPanel fullScreenView = null;
	protected VideoFullScreenViewImpl view;
	protected VideoControlHideTimer controlsHideTimer;
	protected MediaWrapper<?> lastMediaWrapper = null;
	protected MediaWrapper<?> synchronizeWithMediaWrapper = null;

	@PostConstruct
	public void postConstruct() {
		html5FullScreenHelper.addFullScreenEventHandler(this);
	}

	/**
	 * Powiadamia listentery
	 *
	 * @param inFullScreen
	 *            czy jest aktywny fullscreen
	 */
	private void fireEvent(final boolean inFullScreen, final MediaWrapper<?> mediaWrapper) {
		if (inFullScreen) {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_OPEN), mediaWrapper, pageScopeFactory.getCurrentPageScope());
		} else {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_EXIT), mediaWrapper, pageScopeFactory.getCurrentPageScope());
		}
	}

	private void firePlayEvent(final MediaWrapper<?> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY), mediaWrapper, pageScopeFactory.getCurrentPageScope());
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

	private VideoFullScreenViewImpl getFullScreenView() {
		if (view == null) {
			view = new VideoFullScreenViewImpl();
			controlsHideTimer = new VideoControlHideTimer(view);
			RootPanel.get().addDomHandler(this, KeyUpEvent.getType());
		}
		return view;
	}

	protected void clearFullScreenView() {
		if (view != null && view.isAttached()) {
			view.removeFromParent();
			// 0 to kontrolki 1 widget wideo
			view.getContainer().clear();
			view.getControls().clear();
			view.getContainer().add(view.getControls());
		}
	}

	protected void openFullScreenMobile(MediaWrapper<?> mediaWrapper, MediaWrapper<?> fullScreenMediaWrapper) {
		if (dontHaveAnyMediaData(mediaWrapper)) {
			playHTML5Media(mediaWrapper);
		}
		openFullScreenMobileWhenDataReady(mediaWrapper, fullScreenMediaWrapper);
	}

	private void playHTML5Media(MediaWrapper<?> mediaWrapper) {
		((HTML5VideoMediaWrapper)mediaWrapper).getMediaObject().play();
	}

	private boolean dontHaveAnyMediaData(MediaWrapper<?> mediaWrapper) {
		return mediaWrapper instanceof HTML5VideoMediaWrapper && !((HTML5VideoMediaWrapper) mediaWrapper).canPlay();
	}



	private void openFullScreenMobileWhenDataReady(MediaWrapper<?> mediaWrapper, MediaWrapper<?> fullScreenMediaWrapper) {
		html5FullScreenHelper.requestFullScreen(((Widget) mediaWrapper.getMediaObject()).getElement());
		lastMediaWrapper = fullScreenMediaWrapper;
		fireEvent(true, fullScreenMediaWrapper);
		eventsBus.fireEvent(new MediaEvent(MediaEventTypes.ON_MOBILE_FULL_SCREEN_OPEN));
	}

	protected void openFullScreenDesktop(MediaWrapper<?> mediaWrapper, Element template) {
		if (mediaWrapper != null && template != null) {
			clearFullScreenView();
			lastMediaWrapper = mediaWrapper;
			VideoFullScreenViewImpl parent = getFullScreenView();
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
			VideoFullScreenViewImpl parent = getFullScreenView();
			Widget widget = mediaWrapper.getMediaObject();
			parent.getContainer().add(widget);
			resizeToFullScreen(parent, Position.FIXED);
			resizeToFullScreen(widget, Position.ABSOLUTE);
			parseTemplate(mediaWrapper, template, parent.getControls());
			fireEvent(true, mediaWrapper);
			RootPanel.get().add(parent);
			resizeToFullScreen(lastMediaWrapper.getMediaObject(), Position.FIXED);
			resizeToFullScreen(view, Position.FIXED);
			resizeToFullScreen(widget, Position.ABSOLUTE);
		}
	}

	public void openFullScreen(MediaWrapper<?> fullScreenMediaWrapper, MediaWrapper<?> defaultMediaWrapper, Element template) {
		synchronizeWithMediaWrapper = defaultMediaWrapper;
		if (UserAgentChecker.isMobileUserAgent() && !UserAgentChecker.isMobileUserAgent(FIREFOX)) {
			openFullScreenMobile(defaultMediaWrapper, fullScreenMediaWrapper);
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
		if (event.isInFullScreen()) {
			firePlayEvent(lastMediaWrapper);
		} else {
			closeFullScreen();

		}
	}
}
