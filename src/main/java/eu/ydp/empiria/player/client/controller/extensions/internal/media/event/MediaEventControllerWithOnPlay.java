package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class MediaEventControllerWithOnPlay implements MediaEventController {

	@Inject
	private DefaultMediaEventController defaultMediaEventController;

	@Override
	public void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor) {
		switch (((MediaEventTypes) event.getAssociatedType().getType())) {
		case ON_PLAY:
			processor.pauseAllOthers(executor.getMediaWrapper());
			break;
		default:
			defaultMediaEventController.onMediaEvent(event, executor, processor);
			break;
		}
	}

}
