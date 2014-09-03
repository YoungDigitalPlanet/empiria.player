package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

public class PointerDownHandlerImpl implements PointerDownHandler {

	private final TouchOnStartHandler touchOnStartHandler;

	public PointerDownHandlerImpl(final TouchOnStartHandler touchOnStartHandler) {
		this.touchOnStartHandler = touchOnStartHandler;
	}

	@Override
	public void onPointerDown(PointerDownEvent event) {
		if (event.isTouchEvent()) {
			touchOnStartHandler.onStart(event.getNativeEvent());
		}
	}

}
