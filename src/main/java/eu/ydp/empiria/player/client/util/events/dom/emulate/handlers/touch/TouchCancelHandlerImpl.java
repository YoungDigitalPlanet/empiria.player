package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;

public class TouchCancelHandlerImpl implements TouchCancelHandler {

	private final TouchOnCancelHandler touchOnCancelHandler;

	public TouchCancelHandlerImpl(final TouchOnCancelHandler touchOnCancelHandler) {
		this.touchOnCancelHandler = touchOnCancelHandler;
	}

	@Override
	public void onTouchCancel(TouchCancelEvent event) {
		touchOnCancelHandler.onCancel(event.getNativeEvent());
	}

}
