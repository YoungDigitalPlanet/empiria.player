package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.debug.Debug;

public abstract class AbstractMediaProcessor extends InternalExtension implements MediaEventHandler, PlayerEventHandler {
	protected Map<MediaWrapper<?>, SoundExecutor<?>> executors = new HashMap<MediaWrapper<?>, SoundExecutor<?>>();
	protected MediaInteractionSoundEventCallback callback;
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	public AbstractMediaProcessor() {

	}

	@Override
	public void init() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), this);
		for (MediaEventTypes type : MediaEventTypes.values()) {
			eventsBus.addHandler(MediaEvent.getType(type), this);
		}
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		SoundExecutor<?> executor = executors.get(event.getMediaWrapper());
		if (executor == null) {
			Debug.log("Executor is null for " + event.getMediaWrapper());
			return;
		}

		switch (((MediaEventTypes) event.getAssociatedType().getType())) {
		case CHANGE_VOLUME:
			executor.setVolume(event.getValue());
			break;
		case STOP:
			executor.stop();
			break;
		case PAUSE:
			executor.pause();
			break;
		case SET_CURRENT_TIME:
			executor.setCurrentTime(event.getValue());
			break;
		case PLAY:
			executor.play();
			break;
		case ON_PLAY:
			pauseAllOthers(executor.getMediaWrapper());
			break;
		case MUTE:
			executor.setMuted(!(executor.getMediaWrapper().isMuted()));
			break;
		case ENDED:
		case ON_END:
			if (executor.getMediaWrapper().getMediaAvailableOptions().isPauseSupported()) {
				executor.pause();
			} else {
				executor.stop();
			}
			executor.setCurrentTime(0);
			break;

		}
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		switch (event.getType()) {
		case CREATE_MEDIA_WRAPPER:
			createMediaWrapper(event);
			break;
		default:
			break;
		}
	}

	abstract void createMediaWrapper(PlayerEvent event);

	abstract void pauseAllOthers(MediaWrapper<?> mw);
}
