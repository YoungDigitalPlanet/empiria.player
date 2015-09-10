package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import eu.ydp.empiria.player.client.util.events.internal.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface TouchHandler extends EventHandler {
    public void onTouchEvent(TouchEvent event);
}
