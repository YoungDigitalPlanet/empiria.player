package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.types.MediaProcessorExtension;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.reattachhack.HTML5VideoReattachHack;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

import java.util.HashMap;
import java.util.Map;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.isUserAgent;

public abstract class AbstractMediaProcessor extends InternalExtension implements MediaEventHandler, PlayerEventHandler, MediaProcessorExtension {
	private final Map<MediaWrapper<?>, MediaExecutor<?>> executors = new HashMap<MediaWrapper<?>, MediaExecutor<?>>();

	@Inject
	protected EventsBus eventsBus;
	@Inject
	private Provider<HTML5VideoReattachHack> html5VideoReattachHackProvider;
	@Inject
	private Provider<ReCreateAudioHack> reCreateAudioHackProvider;
	@Inject
	private Logger logger;

	private boolean reAttachVideoHackApplied = false;

	@Override
	public final void init() {
		// do nothing
	}

	public void initEvents() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_UNLOADED), this);
		for (MediaEventTypes type : MediaEventTypes.values()) {
			eventsBus.addHandler(MediaEvent.getType(type), this);
		}
	}

	public Map<MediaWrapper<?>, MediaExecutor<?>> getMediaExecutors() {
		return executors;
	}

	public void putMediaExecutor(MediaWrapper<?> wrapper, MediaExecutor<?> executor) {
		executors.put(wrapper, executor);
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		MediaWrapper<?> wrapper = event.getMediaWrapper();
		MediaExecutor<?> executor = executors.get(wrapper);

		if (executor == null) {
			logger.info("media executor is null");
			return;
		}

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
			if (isReAttachHackNeeded(wrapper)) {
				HTML5VideoReattachHack html5VideoReattachHack = html5VideoReattachHackProvider.get();
				html5VideoReattachHack.reAttachVideo((HTML5VideoMediaWrapper) wrapper, (HTML5VideoMediaExecutor) executor);
				reAttachVideoHackApplied = true;
			}
			try {
				executor.play();
			} catch (Exception e) {
				logger.info("AMP PLAY exception: " + getClass().getName() + " " + e.getMessage());
			}
			break;
		case ON_PLAY:
			pauseAllOthers(executor.getMediaWrapper());
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

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		switch (event.getType()) {
		case CREATE_MEDIA_WRAPPER:
			createMediaWrapper(event);
			break;
		case PAGE_UNLOADED:
			pauseAll();
			break;
		default:
			break;
		}
	}

	protected abstract void createMediaWrapper(PlayerEvent event);

	protected abstract void pauseAll();

	protected abstract void pauseAllOthers(MediaWrapper<?> mediaWrapper);

	protected void initExecutor(MediaExecutor<?> executor, BaseMediaConfiguration mediaConfiguration) {
		if (executor != null) {
			executor.setBaseMediaConfiguration(mediaConfiguration);
			executor.init();
			putMediaExecutor(executor.getMediaWrapper(), executor);
		}
	}
}
