package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;

public class DefaultMediaEventController implements MediaEventController {

	@Inject
	private Logger logger;

	@Override
	public void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor) {
		switch (((MediaEventTypes) event.getAssociatedType().getType())) {
		case CHANGE_VOLUME:
			executor.setVolume(event.getVolume());
			break;
		case STOP:
			executor.stop();
			break;
		case PAUSE:
			executor.pause();
			break;
		case RESUME:
			executor.resume();
			break;
		case SET_CURRENT_TIME:
			executor.setCurrentTime(event.getCurrentTime());
			break;
		case PLAY:
			executor.play();
			break;
		case MUTE:
			executor.setMuted(!(executor.getMediaWrapper().isMuted()));
			break;
		case ENDED:
		case ON_END:
			executor.stop();
			break;
		case ON_ERROR:
			logger.info("Media Error");
			break;
		default:
			break;
		}

	}
}
