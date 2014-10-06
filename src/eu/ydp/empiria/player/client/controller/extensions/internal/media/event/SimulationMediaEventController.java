package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class SimulationMediaEventController implements MediaEventController {

	@Inject
	private DefaultMediaEventControllerWithoutOnPlay defaultMediaEventControllerWithoutOnPlay;

	@Override
	public void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor) {
		switch (((MediaEventTypes) event.getAssociatedType().getType())) {
		case ON_PLAY:
			break;
		default:
			defaultMediaEventControllerWithoutOnPlay.onMediaEvent(event, executor, processor);
			break;
		}
	}

}
