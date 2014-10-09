package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.event.MediaEventControllerWithOnPlay;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.event.MediaEventController;
import eu.ydp.empiria.player.client.controller.extensions.types.MediaProcessorExtension;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;

public abstract class AbstractMediaProcessor extends InternalExtension implements MediaEventHandler, PlayerEventHandler, MediaProcessorExtension {
	private final Map<MediaWrapper<?>, MediaExecutor<?>> executors = new HashMap<MediaWrapper<?>, MediaExecutor<?>>();

	@Inject
	protected EventsBus eventsBus;
	@Inject
	private Logger logger;
	@Inject
	private Provider<MediaEventControllerWithOnPlay> defaultMediaEventControllerProvider;

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

		Optional<MediaEventController> eventControllerOpt = executor.getBaseMediaConfiguration().getMediaEventControllerOpt();
		MediaEventController mediaEventController = eventControllerOpt.or(defaultMediaEventControllerProvider.get());
		mediaEventController.onMediaEvent(event, executor, this);
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

	public abstract void pauseAllOthers(MediaWrapper<?> mediaWrapper);

	protected void initExecutor(MediaExecutor<?> executor, BaseMediaConfiguration mediaConfiguration) {
		if (executor != null) {
			executor.setBaseMediaConfiguration(mediaConfiguration);
			executor.init();
			putMediaExecutor(executor.getMediaWrapper(), executor);
		}
	}
}
