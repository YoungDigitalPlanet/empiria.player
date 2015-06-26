package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;

public class SimulationMediaEventController implements MediaEventController {

	@Inject
	private DefaultMediaEventController defaultMediaEventController;

	@Override
	public void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor) {
		defaultMediaEventController.onMediaEvent(event, executor, processor);
	}

}
