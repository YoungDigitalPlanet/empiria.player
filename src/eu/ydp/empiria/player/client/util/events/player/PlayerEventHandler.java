package eu.ydp.empiria.player.client.util.events.player;

import eu.ydp.empiria.player.client.util.events.EventHandler;

public interface PlayerEventHandler extends EventHandler{//<EventScope<?>> {
	void onPlayerEvent(PlayerEvent event);
}

