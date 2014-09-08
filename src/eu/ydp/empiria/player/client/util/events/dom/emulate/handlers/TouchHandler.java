package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.gwtutil.client.event.EventHandler;

public interface TouchHandler extends EventHandler {
	public void onTouchEvent(TouchEvent event);
}
