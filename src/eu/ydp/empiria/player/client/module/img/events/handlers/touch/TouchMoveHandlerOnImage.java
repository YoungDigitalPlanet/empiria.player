package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;

public class TouchMoveHandlerOnImage implements TouchMoveHandler {

	private final TouchToImageEvent touchToImageEvent;

	private final TouchOnImageMoveHandler touchOnImageMoveHandler;

	public TouchMoveHandlerOnImage(TouchOnImageMoveHandler touchOnMoveHandler, final TouchToImageEvent touchEventsCoordinates) {
		this.touchOnImageMoveHandler = touchOnMoveHandler;
		this.touchToImageEvent = touchEventsCoordinates;
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		event.preventDefault();
		touchOnImageMoveHandler.onMove(touchToImageEvent.getTouchOnImageEvent(event));
	}

}
