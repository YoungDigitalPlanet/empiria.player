package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer;

import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;

public interface PointerDownHandler extends EventHandler {
	void onPointerDown(PointerDownEvent event);
}
