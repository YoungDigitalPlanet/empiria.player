package eu.ydp.empiria.player.client.util.events.state;

import eu.ydp.empiria.player.client.util.events.EventHandler;

public interface StateChangeEventHandler extends EventHandler {
	public void onStateChange(StateChangeEvent event);
}
