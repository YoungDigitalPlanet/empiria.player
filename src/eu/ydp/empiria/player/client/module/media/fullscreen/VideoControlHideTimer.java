package eu.ydp.empiria.player.client.module.media.fullscreen;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.user.client.Timer;

import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class VideoControlHideTimer implements TouchEndHandler, ClickHandler, MouseMoveHandler{
	private final Timer controlsHideTimer;
	private static final int CONTROLS_SHOW_TIME = 3000;
	private final VideoFullScreenView view;
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	public VideoControlHideTimer(VideoFullScreenView view) {
		this.view = view;
		view.getContainer().addDomHandler(this, TouchEndEvent.getType());
		view.getContainer().addDomHandler(this, ClickEvent.getType());
		view.getContainer().addDomHandler(this, MouseMoveEvent.getType());
		controlsHideTimer = new Timer() {

			@Override
			public void run() {
				hideCotrols();
			}
		};
		controlsHideTimer.schedule(CONTROLS_SHOW_TIME);
	}

	private void hideCotrols() {
		view.getControls().setVisible(false);
	}

	private void showControls() {
		if (!view.getControls().isVisible()) {
			view.getControls().setVisible(true);
			eventsBus.fireEvent(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_SHOW_CONTROLS));
		}
		controlsHideTimer.cancel();
		controlsHideTimer.schedule(CONTROLS_SHOW_TIME);
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		showControls();
	}

	@Override
	public void onClick(ClickEvent event) {
		showControls();
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		showControls();
	}
}
