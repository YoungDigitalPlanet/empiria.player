package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

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
