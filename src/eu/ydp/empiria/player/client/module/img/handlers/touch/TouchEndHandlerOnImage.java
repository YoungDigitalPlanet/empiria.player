package eu.ydp.empiria.player.client.module.img.handlers.touch;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;

import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchToImageEvent;

public class TouchEndHandlerOnImage implements TouchEndHandler {

	private final TouchToImageEvent touchToImageEvent;

	private final TouchOnImageEndHandler touchOnEndHandler;

	public TouchEndHandlerOnImage(final TouchOnImageEndHandler touchOnEndHandler, final TouchToImageEvent touchEventsCoordinates) {
		this.touchOnEndHandler = touchOnEndHandler;
		this.touchToImageEvent = touchEventsCoordinates;
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		event.preventDefault();
		touchOnEndHandler.onEnd(touchToImageEvent.getTouchOnImageEvent(event));
	}
}
