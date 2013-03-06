package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.isUserAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.AttachHandlerFactory;
import eu.ydp.empiria.player.client.module.media.html5.AttachHandlerImpl;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoReattachHack;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentChecker.RuntimeMobileUserAgent;

public abstract class AbstractMediaProcessor extends InternalExtension implements MediaEventHandler, PlayerEventHandler {
	private final Map<MediaWrapper<?>, MediaExecutor<?>> executors = new HashMap<MediaWrapper<?>, MediaExecutor<?>>();

	protected MediaInteractionSoundEventCallback callback;

	@Inject
	protected EventsBus eventsBus;

	@Inject
	private AttachHandlerFactory attachHandlerFactory;

	private boolean reAttachVideoHackApplied = false;

	@Override
	public void init() {
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
			Logger.getLogger(getClass().getName()).info("Media Executor is null");
			return;
		}

		switch (((MediaEventTypes) event.getAssociatedType().getType())) {
			case CHANGE_VOLUME:
				executor.setVolume( event.getVolume());
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
				if (isUserAgent(RuntimeMobileUserAgent.ANDROID404)
						&& UserAgentChecker.isStackAndroidBrowser()
						&& executor.getBaseMediaConfiguration().isFeedback()) {
					reCreateAudio((AbstractHTML5MediaWrapper) wrapper, (AbstractHTML5MediaExecutor) executor);
				}

				if (wrapper instanceof HTML5VideoMediaWrapper && isUserAgent(MobileUserAgent.SAFARI_WEBVIEW)
						&& !reAttachVideoHackApplied) {
					reAttachVideoHack((AbstractHTML5MediaWrapper) wrapper, (AbstractHTML5MediaExecutor) executor);
					reAttachVideoHackApplied = true;
				}

				try {
					executor.play();
				} catch (Exception e) {
					Logger.getLogger(getClass().getName()).info(e.getMessage());
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
				if (isUserAgent(RuntimeMobileUserAgent.ANDROID404)
						&& UserAgentChecker.isStackAndroidBrowser()) {
					reCreateAudio((AbstractHTML5MediaWrapper) wrapper, (AbstractHTML5MediaExecutor) executor);
				}
				executor.stop();
				break;
			case ON_ERROR:
				Logger.getLogger(getClass().getName()).info("Media Error");
				break;
			default:
				break;
		}
	}


	private void reAttachVideoHack(AbstractHTML5MediaWrapper mediaWrapper, AbstractHTML5MediaExecutor mediaExecutor) {
		HTML5VideoReattachHack html5VideoReattachHack = new HTML5VideoReattachHack();
		html5VideoReattachHack.setEventsBus(eventsBus);
		AttachHandlerImpl attachHandler = attachHandlerFactory.createAttachHandler(mediaExecutor, mediaWrapper);
		html5VideoReattachHack.reAttachVideo(mediaWrapper, mediaExecutor, attachHandler);
	}

	protected void reCreateAudio(AbstractHTML5MediaWrapper wrapper, AbstractHTML5MediaExecutor executor) {
		Audio audio = (Audio) wrapper.getMediaObject();
		Audio newAudio = reAttachAudio(audio);
		wrapper.setMediaObject(newAudio);
		executor.setMedia(newAudio);
		executor.init();
	}

	protected Audio reAttachAudio(Audio audio) {
		NodeList<Node> sourceList = audio.getAudioElement().getChildNodes();
		FlowPanel parent = (FlowPanel) audio.getParent();

		parent.remove(audio);
		Audio newAudio = new Audio();
		parent.add(newAudio);

		for (int i = 0; i < sourceList.getLength(); i++) {
			newAudio.getElement().appendChild(sourceList.getItem(i));
		}

		return newAudio;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		switch (event.getType()) {
		case CREATE_MEDIA_WRAPPER:
			createMediaWrapper(event);
			break;
		case PAGE_UNLOADED:
			//FIXME scope for executors
		//	removeUnusedExecutors();
			break;
		default:
			break;
		}
	}

	protected abstract void createMediaWrapper(PlayerEvent event);

	protected abstract void pauseAllOthers(MediaWrapper<?> mediaWrapper);
}
