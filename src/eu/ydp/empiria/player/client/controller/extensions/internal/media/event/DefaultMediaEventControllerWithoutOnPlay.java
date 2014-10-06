package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.*;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.reattachhack.HTML5VideoReattachHack;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class DefaultMediaEventControllerWithoutOnPlay implements MediaEventController {

	@Inject
	private Logger logger;
	@Inject
	private Provider<HTML5VideoReattachHack> html5VideoReattachHackProvider;

	private final boolean reAttachVideoHackApplied = false;

	@Override
	public void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor) {
		MediaWrapper<?> wrapper = event.getMediaWrapper();

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
		case SET_CURRENT_TIME:
			executor.setCurrentTime(event.getCurrentTime());
			break;
		case PLAY:
			// if (isReAttachHackNeeded(wrapper)) {
			// HTML5VideoReattachHack html5VideoReattachHack =
			// html5VideoReattachHackProvider.get();
			// html5VideoReattachHack.reAttachVideo((HTML5VideoMediaWrapper)
			// wrapper, (HTML5VideoMediaExecutor) executor);
			// reAttachVideoHackApplied = true;
			// }
			// try {
			// executor.play();
			// } catch (Exception e) {
			// logger.info("AMP PLAY exception: " + getClass().getName() + " " +
			// e.getMessage());
			// }

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

	private boolean isReAttachHackNeeded(MediaWrapper<?> wrapper) {
		return wrapper instanceof HTML5VideoMediaWrapper && isUserAgent(MobileUserAgent.SAFARI_WEBVIEW) && !reAttachVideoHackApplied;
	}

}
