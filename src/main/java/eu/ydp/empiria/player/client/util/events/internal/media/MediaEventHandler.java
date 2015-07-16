package eu.ydp.empiria.player.client.util.events.internal.media;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface MediaEventHandler extends EventHandler {
    void onMediaEvent(MediaEvent event);
}
