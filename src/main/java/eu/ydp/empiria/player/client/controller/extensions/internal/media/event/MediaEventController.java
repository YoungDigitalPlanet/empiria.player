package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;

public interface MediaEventController {
    void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor);
}
