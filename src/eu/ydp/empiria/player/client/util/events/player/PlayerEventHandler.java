package eu.ydp.empiria.player.client.util.events.player;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface PlayerEventHandler extends EventHandler{//<EventScope<?>> {
	void onPlayerEvent(PlayerEvent event);
}

