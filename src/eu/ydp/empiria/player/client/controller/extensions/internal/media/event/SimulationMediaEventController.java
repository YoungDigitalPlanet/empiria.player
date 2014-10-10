package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;

public class SimulationMediaEventController implements MediaEventController {

	@Inject
	private DefaultMediaEventController defaultMediaEventControllerWithoutOnPlay;

	@Override
	public void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor) {
		defaultMediaEventControllerWithoutOnPlay.onMediaEvent(event, executor, processor);
	}

}
