package eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers;

import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;

public interface PointerDownHandler extends EventHandler {
	void onPointerDown(PointerDownEvent event);
}
