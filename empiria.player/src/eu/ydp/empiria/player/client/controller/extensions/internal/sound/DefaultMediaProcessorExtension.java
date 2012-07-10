package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import java.util.HashSet;
import java.util.Map;
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
import eu.ydp.empiria.player.client.controller.extensions.types.SoundProcessorExtension;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.HTML5AudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;

public class DefaultMediaProcessorExtension extends AbstractMediaProcessor implements SoundProcessorExtension, SoundExecutorListener {

	protected SoundExecutor<?> soundExecutor;
	protected boolean muteFeedbacks = false;
	protected Set<MediaWrapper<?>> mediaSet = new HashSet<MediaWrapper<?>>();

	public DefaultMediaProcessorExtension() {
	}

	@Override
	public void init() {
		super.init();
		soundExecutor = GWT.create(SoundExecutor.class);
		soundExecutor.setSoundFinishedListener(this);
		executors.put(null, soundExecutor);
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
			forceStop(false);
			if (soundExecutor instanceof ExecutorSwf) {
				((ExecutorSwf) soundExecutor).free();
			}
		} else if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_MUTE) {
			if (deliveryEvent.getParams().containsKey("mute") && deliveryEvent.getParams().get("mute") instanceof Boolean) {
				muteFeedbacks = (Boolean) deliveryEvent.getParams().get("mute");
			}
		} else if ((deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND && !muteFeedbacks) || deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY) {

			if (deliveryEvent.getParams().containsKey("url") && deliveryEvent.getParams().get("url") instanceof String) {
				String url = (String) deliveryEvent.getParams().get("url");
				forceStop(false);
				if (soundExecutor instanceof ExecutorSwf) {
					((ExecutorSwf) soundExecutor).free();
				}
				callback = null;
				if (deliveryEvent.getParams().containsKey("callback") && deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback) {
					callback = ((MediaInteractionSoundEventCallback) deliveryEvent.getParams().get("callback"));
				}

				if (callback != null) {
					callback.setCallforward(new MediaInteractionSoundEventCallforward() {
						@Override
						public void stop() {
							forceStop(false);
							if (soundExecutor instanceof ExecutorSwf) {
								((ExecutorSwf) soundExecutor).free();
							}
						}
					});
				}
				soundExecutor.play(url);
			}
		}
	}

	@Override
	void pause(MediaWrapper<?> mw) {
		forceStop(true, mw);
	}

	protected void forceStop(boolean pause) {
		forceStop(pause, null);
	}

	protected void forceStop(boolean pause, MediaWrapper<?> mw) {
		for (SoundExecutor<?> se : executors.values()) {
			if (se.getMediaWrapper() !=null && se.getMediaWrapper().equals(mw)) {
				continue;
			}
			if (se.getMediaWrapper() !=null && se.getMediaWrapper().getMediaAvailableOptions().isPauseSupported() && pause) {
				se.pause();
			} else {
				se.stop();
			}
		}
		if (executors.size() == 0) {
			soundExecutor.stop();
		}
	}

	@Override
	public void onSoundFinished() {
		if (callback != null) {
			callback.onStop();
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
		if (event.getValue() != null && event.getValue() instanceof BaseMediaConfiguration) {
			BaseMediaConfiguration bmc = (BaseMediaConfiguration) event.getValue();
			Media mb = null;
			boolean geckoSupport = true;
			//lecim z koksem :D
			if (!containsOgg(bmc.getSources()) && (UserAgentChecker.isUserAgent(UserAgent.GECKO1_8) || UserAgentChecker.isMobileUserAgent(MobileUserAgent.FIREFOX))) {
				geckoSupport = false;
			}
			if (bmc.getMediaType() == MediaType.VIDEO && Video.isSupported() && geckoSupport) {
				mb = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
			} else if (Audio.isSupported() && geckoSupport) {
				mb = new HTML5AudioImpl();
			}

			SoundExecutor<?> executor;
			if (mb != null) {
				HTML5MediaExecutor ex = new HTML5MediaExecutor();
				ex.setMediaWrapper(new HTML5MediaWrapper(mb));
				executor = ex;
			} else if (!UserAgentChecker.isLocal()) {
				if (bmc.isTemplate()) {
					if (bmc.getMediaType() == MediaType.VIDEO) {
						VideoExecutorSwf ex = new VideoExecutorSwf();
						ex.setMediaWrapper(new SwfMediaWrapper());
						executor = ex;
					} else {
						SoundExecutorSwf ex = new SoundExecutorSwf();
						ex.setMediaWrapper(new SwfMediaWrapper());
						executor = ex;
					}
				} else {
					OldSwfMediaExecutor ex = new OldSwfMediaExecutor();
					ex.setMediaWrapper(new OldSwfMediaWrapper());
					executor = ex;
				}
			} else {
				executor = new LocalSwfMediaExecutor();
				executor.setMediaWrapper((MediaWrapper) new LocalSwfMediaWrapper());
			}
			executor.setBaseMediaConfiguration(bmc);
			executor.init();
			executors.put(executor.getMediaWrapper(), executor);
			if (event.getSource() instanceof CallbackRecevier) {
				((CallbackRecevier) event.getSource()).setCallbackReturnObject(executor.getMediaWrapper());
			}
			//soundExecutor = executor;
		}
	}

	/**
	 * sprawdza czy w elementach soirce mamy pliki typu ogg
	 *
	 * @param sources
	 * @return
	 */
	private boolean containsOgg(Map<String, String> sources) {
		for (Map.Entry<String, String> src : sources.entrySet()) {
			if (src.getValue().matches(".*ogv|.*ogg")) {
				return true;
			}
		}
		return false;
	}
}
