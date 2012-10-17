package eu.ydp.empiria.player.client.util.events.connection;

import eu.ydp.empiria.player.client.util.events.EventHandler;

public interface ConnectionModuleEventHandler extends EventHandler {
	
	void onConnectionModuleEvent(ConnectionEvent event);	
}
