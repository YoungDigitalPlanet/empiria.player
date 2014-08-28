package eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.handlers;

import com.google.gwt.event.shared.EventHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent.events.PointerDownEvent;

public interface PointerDownHandler extends EventHandler {
	void onPointerDown(PointerDownEvent event);
}
