package eu.ydp.empiria.player.client.controller.window;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class WindowResizeTimer extends Timer {

    private final PlayerEvent windowResized = new PlayerEvent(PlayerEventTypes.WINDOW_RESIZED);

    @Inject
    private EventsBus eventsBus;

    @Override
    public void run() {
        eventsBus.fireAsyncEvent(windowResized);
    }
}
