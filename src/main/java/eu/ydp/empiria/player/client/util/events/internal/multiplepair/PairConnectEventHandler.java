package eu.ydp.empiria.player.client.util.events.internal.multiplepair;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface PairConnectEventHandler extends EventHandler {
    public void onConnectionEvent(PairConnectEvent event);
}
