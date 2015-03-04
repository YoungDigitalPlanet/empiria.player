package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;

public class PointerMoveHandlerImpl implements PointerMoveHandler {

	private final TouchOnMoveHandler touchOnMoveHandler;

	@Inject
	public PointerMoveHandlerImpl(@Assisted TouchOnMoveHandler touchOnMoveHandler) {
		this.touchOnMoveHandler = touchOnMoveHandler;
	}

	@Override
	public void onPointerMove(PointerMoveEvent event) {
		if (event.isTouchEvent()) {
			touchOnMoveHandler.onMove(event.getNativeEvent());
		}
	}

}
