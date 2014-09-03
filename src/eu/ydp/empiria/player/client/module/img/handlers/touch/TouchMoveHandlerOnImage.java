package eu.ydp.empiria.player.client.module.img.handlers.touch;

import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;

import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchToImageEvent;

public class TouchMoveHandlerOnImage implements TouchMoveHandler {

	private final TouchToImageEvent touchToImageEvent;

	private final TouchOnImageMoveHandler touchOnMoveHandler;

	public TouchMoveHandlerOnImage(TouchOnImageMoveHandler touchOnMoveHandler, final TouchToImageEvent touchEventsCoordinates) {
		this.touchOnMoveHandler = touchOnMoveHandler;
		this.touchToImageEvent = touchEventsCoordinates;
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		event.preventDefault();
		touchOnMoveHandler.onMove(touchToImageEvent.getTouchOnImageEvent(event));
	}

}
