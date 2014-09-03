package eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers;

import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;

public interface PointerMoveHandler extends EventHandler {
	void onPointerMove(PointerMoveEvent event);
}
