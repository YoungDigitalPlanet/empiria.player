package eu.ydp.empiria.player.client.util.events.internal.state;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface StateChangeEventHandler extends EventHandler {
    public void onStateChange(StateChangeEvent event);
}
