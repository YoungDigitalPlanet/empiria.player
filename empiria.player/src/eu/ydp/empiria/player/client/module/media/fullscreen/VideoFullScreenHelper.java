package eu.ydp.empiria.player.client.module.media.fullscreen;

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

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;

public class VideoFullScreenHelper implements KeyUpHandler {

	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected ObjectTemplateParser<?> parser;

	@Inject
	protected GWTPanelFactory panelFactory;

	@Inject
	protected StyleNameConstants styleNames;

	protected FlowPanel fullScreenView = null;
	protected VideoFullScreenViewImpl view;
	protected VideoControlHideTimer controlsHideTimer;
	protected MediaWrapper<?> lastMediaWrapper = null;
	/**
	 * Powiadamia listentery
	 *
	 * @param inFullScreen
	 *            czy jest aktywny fullscreen
	 */
	private void fireEvent(final boolean inFullScreen, final MediaWrapper<?> mediaWrapper) {
		if (inFullScreen) {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_OPEN), mediaWrapper, new CurrentPageScope());
		} else {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_EXIT), mediaWrapper, new CurrentPageScope());
		}
	}

	protected void pauseCurrentVideo() {
		eventsBus.fireEvent(new MediaEvent(MediaEventTypes.PAUSE));
	}

	protected FlowPanel parseTemplate(MediaWrapper<?> mediaWrapper, Element template, FlowPanel parent) {
		parser.setMediaWrapper(mediaWrapper);
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
		style.setBackgroundColor("black");
	}

	private VideoFullScreenViewImpl getFullScreenView() {
		if (view == null) {
			view = new VideoFullScreenViewImpl();
			controlsHideTimer = new VideoControlHideTimer(view);
			RootPanel.get().addDomHandler(this, KeyUpEvent.getType());
		}
		resizeToFullScreen(view, Position.FIXED);
		return view;
	}

	private void clearFullScreenView() {
		if (view != null && view.isAttached()) {
			view.removeFromParent();
			// 0 to kontrolki 1 widget wideo
			view.getContainer().clear();
			view.getControls().clear();
			view.getContainer().add(view.getControls());
		}
	}

	public void openFullScreen(MediaWrapper<?> mediaWrapper, Element template) {
		if (mediaWrapper != null && template != null) {
			pauseCurrentVideo();
			clearFullScreenView();
			lastMediaWrapper = mediaWrapper;
			VideoFullScreenViewImpl parent = getFullScreenView();
			Widget widget = mediaWrapper.getMediaObject();
			//do poprawy odtwarzacz flashowy
//			if(mediaWrapper instanceof SwfMediaWrapper){
//				FlashVideo video = (FlashVideo) ((SwfMediaWrapper) mediaWrapper).getFlashMedia();
//				video.setSize(Window.getClientWidth(), Window.getClientHeight());
//			}
			resizeToFullScreen(widget, Position.ABSOLUTE);
			parent.getContainer().add(widget);
			parseTemplate(mediaWrapper, template, parent.getControls());
			fireEvent(true, mediaWrapper);
			RootPanel.get().add(parent);
		}
	}

	public void closeFullScreen() {
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
}
