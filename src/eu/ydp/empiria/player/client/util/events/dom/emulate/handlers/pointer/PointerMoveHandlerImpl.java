package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerMoveHandler;

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
