package eu.ydp.empiria.player.client.util.events.connection;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface ConnectionEventHandler extends EventHandler {
	public void onConnectionEvent(ConnectionEvent event);
}
