package eu.ydp.empiria.player.client.module.img.handlers.touch;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;

import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchToImageEvent;

public class TouchStartHandlerOnImage implements TouchStartHandler {

	private final TouchToImageEvent touchToImageEvent;

	private final TouchOnImageStartHandler touchStartHandler;

	public TouchStartHandlerOnImage(final TouchOnImageStartHandler touchStartHandler, final TouchToImageEvent touchEventsCoordinates) {
		this.touchStartHandler = touchStartHandler;
		this.touchToImageEvent = touchEventsCoordinates;
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		event.preventDefault();
		touchStartHandler.onStart(touchToImageEvent.getTouchOnImageEvent(event));
	}
}
