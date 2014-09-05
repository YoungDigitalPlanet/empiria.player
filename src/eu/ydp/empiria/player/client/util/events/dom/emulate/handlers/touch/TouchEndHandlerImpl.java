package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;

public class TouchEndHandlerImpl implements TouchEndHandler {

	private final TouchOnEndHandler touchOnEndHandler;

	public TouchEndHandlerImpl(final TouchOnEndHandler touchOnEndHandler) {
		this.touchOnEndHandler = touchOnEndHandler;
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		touchOnEndHandler.onEnd(event.getNativeEvent());
	}
}
