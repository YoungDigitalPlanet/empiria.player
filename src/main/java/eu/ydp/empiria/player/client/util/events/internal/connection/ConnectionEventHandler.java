package eu.ydp.empiria.player.client.util.events.internal.connection;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface ConnectionEventHandler extends EventHandler {
    public void onConnectionEvent(ConnectionEvent event);
}
