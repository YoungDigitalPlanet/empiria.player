package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.isMobileUserAgent;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.isUserAgent;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.Video;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.LocalSwfMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.LocalSwfMediaWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.OldSwfMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.OldSwfMediaWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.SwfMediaWrapper;
import eu.ydp.empiria.player.client.controller.extensions.types.MediaProcessorExtension;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrappersPair;
import eu.ydp.empiria.player.client.module.media.html5.HTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.HTML5AudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;

public class DefaultMediaProcessorExtension extends AbstractMediaProcessor implements MediaProcessorExtension, SoundExecutorListener {
	protected boolean muteFeedbacks = false;
	protected Set<MediaWrapper<?>> mediaSet = new HashSet<MediaWrapper<?>>();
	protected boolean initialized = false;
	protected MediaExecutor<?> feedbackSoundExecutor = null;//NOPMD
	protected MediaExecutor<?> html5SoundExecutor = null;//NOPMD

	@Override
	public void init() {
		if (!initialized) {
			super.init();
			feedbackSoundExecutor = GWT.create(MediaExecutor.class);
			feedbackSoundExecutor.setSoundFinishedListener(this);
			if (Audio.isSupported()){
				html5SoundExecutor = new SoundExecutorHtml5();
				html5SoundExecutor.setSoundFinishedListener(this);
			}
			initialized = true;
		}
	}

	public void getSoundExecutor() {

	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_LISTENER_DELIVERY_EVENTS;
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.PAGE_UNLOADING) {
			forceStop(true, true);
		} else if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_MUTE) {
			if (deliveryEvent.getParams().containsKey("mute") && deliveryEvent.getParams().get("mute") instanceof Boolean) {
				muteFeedbacks = (Boolean) deliveryEvent.getParams().get("mute");
			}
		} else if ((deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND && !muteFeedbacks) || deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY) {

			if (deliveryEvent.getParams().containsKey("url") && deliveryEvent.getParams().get("url") instanceof String) {
				String url = (String) deliveryEvent.getParams().get("url");
				forceStop(true, true);
				callback = null;
				if (deliveryEvent.getParams().containsKey("callback") && deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback) {
					callback = ((MediaInteractionSoundEventCallback) deliveryEvent.getParams().get("callback"));
				}

				if (callback != null) {
					callback.setCallforward(new MediaInteractionSoundEventCallforward() {
						@Override
						public void stop() {
							forceStop(true, true);
						}
					});
				}
				if (!MediaChecker.isHtml5Mp3Support()  &&  html5SoundExecutor != null  &&  url.toLowerCase().endsWith(".ogg")){
					html5SoundExecutor.play(url);
				} else {
					feedbackSoundExecutor.play(url);
				}
			}
		}
	}

	@Override
	protected void pauseAllOthers(MediaWrapper<?> mediaWrapper) {
		forceStop(true, mediaWrapper, true);
	}

	protected void forceStop(boolean pause, boolean stopDefaultSoundExecutor) {
		forceStop(pause, null, stopDefaultSoundExecutor);
	}

	protected void forceStop(boolean pause, MediaWrapper<?> mw, boolean stopDefaultSoundExecutor) {
		for (MediaExecutor<?> se : executors.values()) {
			if (se.getMediaWrapper() != null && se.getMediaWrapper().equals(mw)) {
				continue;
			}
			if (se.getMediaWrapper() != null && se.getMediaWrapper().getMediaAvailableOptions().isPauseSupported() && pause) {
				se.pause();
			} else if (se.getMediaWrapper() != null || (se.getMediaWrapper() == null && mw != null)) {
				se.stop();
			}
		}
		if (stopDefaultSoundExecutor){
			feedbackSoundExecutor.stop();
			if (html5SoundExecutor != null){
				html5SoundExecutor.stop();
			}
		}
		if (mw != null) {
			callback = null;
		}
	}

	@Override
	public void onSoundFinished() {
		if (callback != null) {
			callback.onStop();
			callback = null;
		}

	}

	@Override
	public void onPlay() {
		if (callback != null) {
			callback.onPlay();
		}
	}

	/**
	 * tworzy obiekt wrappera oraz executora
	 *
	 * @param event
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void createMediaWrapper(PlayerEvent event) {
		if (event.getValue() instanceof BaseMediaConfiguration) {
			BaseMediaConfiguration bmc = (BaseMediaConfiguration) event.getValue();
			Media defaultMedia = null;
			Media fullScreenMedia = null;
			boolean geckoSupport = isGeckoSupport(bmc);

			if (bmc.getMediaType() == MediaType.VIDEO && Video.isSupported() && geckoSupport) {
				defaultMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
				if (bmc.isFullScreenTemplate()) {
					fullScreenMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
				}
			} else if (Audio.isSupported() && geckoSupport) {
				defaultMedia = new HTML5AudioImpl();
			}

			MediaExecutor<?> executor;
			MediaExecutor<?> fullScreenExecutor = null;
			if (!UserAgentChecker.isLocal() && defaultMedia == null) {
				if (bmc.isTemplate()) {
					if (bmc.getMediaType() == MediaType.VIDEO) {
						executor = createSWFVideoMediaExecutor();
						if (bmc.isFullScreenTemplate()) {
							fullScreenExecutor = createSWFVideoMediaExecutor();
						}
					} else {
						executor = createSWFSoundMediaExecutor();
					}
				} else {
					OldSwfMediaExecutor exc = new OldSwfMediaExecutor();
					exc.setMediaWrapper(new OldSwfMediaWrapper());
					executor = exc;
				}
			} else if (defaultMedia == null && UserAgentChecker.isLocal()) {
				executor = new LocalSwfMediaExecutor();
				executor.setMediaWrapper((MediaWrapper) new LocalSwfMediaWrapper());
			} else {
				executor = createHTML5MediaExecutor(defaultMedia);
				fullScreenExecutor = createHTML5MediaExecutor(fullScreenMedia);
			}

			initExecutor(executor, bmc);
			initExecutor(fullScreenExecutor, bmc);
			fireCallback(event, executor, fullScreenExecutor);
		}
	}

	private void fireCallback(PlayerEvent event, MediaExecutor<?> defaultMediaExecutor, MediaExecutor<?> fullScreenMediaExecutor) {
		if (event.getSource() instanceof CallbackRecevier) {
			if (fullScreenMediaExecutor == null) {
				((CallbackRecevier) event.getSource()).setCallbackReturnObject(defaultMediaExecutor.getMediaWrapper());
			} else {
				((CallbackRecevier) event.getSource()).setCallbackReturnObject(new MediaWrappersPair(defaultMediaExecutor.getMediaWrapper(), fullScreenMediaExecutor
						.getMediaWrapper()));
			}
		}
	}

	private boolean isGeckoSupport(BaseMediaConfiguration bmc) {
		boolean geckoSupport = true;
		if (!SourceUtil.containsOgg(bmc.getSources()) && (isUserAgent(UserAgent.GECKO1_8) || isUserAgent(UserAgent.OPERA) || isMobileUserAgent(MobileUserAgent.FIREFOX))) {
			geckoSupport = false;
		}
		return geckoSupport;
	}

	private void initExecutor(MediaExecutor<?> executor, BaseMediaConfiguration mediaConfiguration) {
		if (executor != null) {
			executor.setBaseMediaConfiguration(mediaConfiguration);
			executor.init();
			executors.put(executor.getMediaWrapper(), executor);
		}
	}

	private MediaExecutor<?> createHTML5MediaExecutor(Media media) {
		HTML5MediaExecutor executor = null;
		if (media != null) {
			executor = new HTML5MediaExecutor();
			executor.setMediaWrapper(new HTML5MediaWrapper(media));
			media.setEventBusSourceObject(executor.getMediaWrapper());
		}
		return executor;
	}

	private MediaExecutor<?> createSWFVideoMediaExecutor() {
		VideoExecutorSwf executor = new VideoExecutorSwf();
		executor.setMediaWrapper(new SwfMediaWrapper());
		return executor;
	}

	private MediaExecutor<?> createSWFSoundMediaExecutor() {
		SoundExecutorSwf executor = new SoundExecutorSwf();
		executor.setMediaWrapper(new SwfMediaWrapper());
		return executor;
	}

}
