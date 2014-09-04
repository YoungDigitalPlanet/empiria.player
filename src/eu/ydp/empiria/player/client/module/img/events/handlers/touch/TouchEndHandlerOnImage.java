package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;

public class TouchEndHandlerOnImage implements TouchEndHandler {

	private final TouchToImageEvent touchToImageEvent;

	private final TouchOnImageEndHandler touchOnEndHandler;

	public TouchEndHandlerOnImage(final TouchOnImageEndHandler touchOnEndHandler, final TouchToImageEvent touchToImageEvent) {
		this.touchOnEndHandler = touchOnEndHandler;
		this.touchToImageEvent = touchToImageEvent;
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		event.preventDefault();
		touchOnEndHandler.onEnd(touchToImageEvent.getTouchOnImageEvent(event));
	}
}
