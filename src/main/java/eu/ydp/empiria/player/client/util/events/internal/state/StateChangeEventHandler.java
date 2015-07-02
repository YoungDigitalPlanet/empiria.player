package eu.ydp.empiria.player.client.util.events.internal.state;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface StateChangeEventHandler extends EventHandler {
	public void onStateChange(StateChangeEvent event);
}
