package eu.ydp.empiria.player.client.util.events.multiplepair;

import eu.ydp.empiria.player.client.util.events.EventHandler;

public interface PairConnectEventHandler extends EventHandler {
	public void onConnectionEvent(PairConnectEvent event);
}
