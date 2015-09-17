package eu.ydp.empiria.player.client.util.events.internal.player;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface PlayerEventHandler extends EventHandler {// <EventScope<?>> {

    void onPlayerEvent(PlayerEvent event);
}
