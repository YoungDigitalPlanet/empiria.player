package eu.ydp.empiria.player.client.util.events.internal.media;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface MediaEventHandler extends EventHandler {
    void onMediaEvent(MediaEvent event);
}
