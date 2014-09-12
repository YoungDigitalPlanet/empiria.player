package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;

public class PointerMoveHandlerImpl implements PointerMoveHandler {

	private final TouchOnMoveHandler touchOnMoveHandler;

	public PointerMoveHandlerImpl(final TouchOnMoveHandler touchOnMoveHandler) {
		this.touchOnMoveHandler = touchOnMoveHandler;
	}

	@Override
	public void onPointerMove(PointerMoveEvent event) {
		if (event.isTouchEvent()) {
			touchOnMoveHandler.onMove(event.getNativeEvent());
		}
	}

}
