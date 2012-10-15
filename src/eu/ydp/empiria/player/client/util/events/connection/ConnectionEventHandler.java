package eu.ydp.empiria.player.client.util.events.connection;

import eu.ydp.empiria.player.client.util.events.EventHandler;

public interface ConnectionEventHandler extends EventHandler {
	public void onConnectionEvent(ConnectionEvent event);
}
