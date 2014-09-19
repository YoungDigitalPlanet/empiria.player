package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;

public interface PointerMoveHandler extends EventHandler {
	void onPointerMove(PointerMoveEvent event);
}
