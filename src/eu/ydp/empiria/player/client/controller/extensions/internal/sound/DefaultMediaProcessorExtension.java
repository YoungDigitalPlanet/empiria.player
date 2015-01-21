package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.LocalSwfMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.LocalSwfMediaWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.OldSwfMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.OldSwfMediaWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.SwfMediaWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoAvailability;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.factory.HTML5MediaExecutorFactory;
import eu.ydp.empiria.player.client.gin.factory.MediaWrappersPairFactory;
import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrappersPair;
import eu.ydp.empiria.player.client.module.object.impl.ExternalFullscreenVideoImpl;
import eu.ydp.empiria.player.client.module.object.impl.HTML5AudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class DefaultMediaProcessorExtension extends AbstractMediaProcessor {
	protected Set<MediaWrapper<?>> mediaSet = new HashSet<MediaWrapper<?>>();
	protected boolean initialized;

	@Inject
	private MediaWrappersPairFactory pairFactory;
	@Inject
	private MediaExecutorsStopper mediaExecutorsStopper;
	@Inject
	private Instance<HTML5MediaExecutorFactory> html5MediaExecutorFactoryProvider;
	@Inject
	private ExternalFullscreenVideoAvailability externalFullscreenVideoAvailability;
	@Inject
	private Provider<FullscreenVideoExecutor> fullscreenVideoExecutorProvider;
	@Inject
	private Provider<SoundExecutorSwfSimple> simpleSwfExecutorProvider;
	@Inject
	private Provider<LocalSwfMediaExecutor> localSwfExecutorProvider;
	@Inject
	private Provider<LocalSwfMediaWrapper> localSwfWrapperProvider;
	@Inject
	private MediaChecker mediaChecker;

	@Override
	public void initMediaProcessor() {
		if (!initialized) {
			initEvents();
			initialized = true;
		}
	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_LISTENER_DELIVERY_EVENTS;
	}

	@Override
	public void pauseAllOthers(MediaWrapper<?> mediaWrapper) {
		forceStop(mediaWrapper);
	}

	@Override
	protected void pauseAll() {
		forceStop(null);
	}

	protected void forceStop(MediaWrapper<?> mw) {
		mediaExecutorsStopper.forceStop(mw, getMediaExecutors().values());
	}

	@Override
	protected void createMediaWrapper(PlayerEvent event) {
		if (event.getValue() instanceof BaseMediaConfiguration) {
			BaseMediaConfiguration bmc = (BaseMediaConfiguration) event.getValue();
			Media defaultMedia = null;
			Media fullScreenMedia = null;
			boolean geckoSupport = isGeckoSupport(bmc);

			if (bmc.getMediaType() == MediaType.VIDEO && externalFullscreenVideoAvailability.isAvailable()) {
				defaultMedia = new ExternalFullscreenVideoImpl();
			} else if (bmc.getMediaType() == MediaType.VIDEO && Video.isSupported() && geckoSupport) {
				defaultMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
				if (bmc.isFullScreenTemplate()) {
					fullScreenMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
				}
			} else if (Audio.isSupported() && geckoSupport) {
				defaultMedia = new HTML5AudioImpl();
			}

			MediaExecutor<Widget> executor;
			MediaExecutor<Widget> fullScreenExecutor = null;

			if (bmc.getMediaType() == MediaType.VIDEO && externalFullscreenVideoAvailability.isAvailable()) {
				executor = fullscreenVideoExecutorProvider.get();
			} else if (!UserAgentChecker.isLocal() && defaultMedia == null) {
				if (bmc.isTemplate() || bmc.isFeedback()) {
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
				if (bmc.isFeedback()) {
					executor = simpleSwfExecutorProvider.get();
				} else {
					executor = localSwfExecutorProvider.get();
					MediaWrapper<Widget> mediaWrapper = localSwfWrapperProvider.get();
					executor.setMediaWrapper(mediaWrapper);
				}
			} else {
				executor = createHTML5MediaExecutor(defaultMedia, bmc.getMediaType());
				fullScreenExecutor = createHTML5MediaExecutor(fullScreenMedia, bmc.getMediaType());
			}

			initExecutor(executor, bmc);
			initExecutor(fullScreenExecutor, bmc);
			fireCallback(event, executor, fullScreenExecutor);
		}
	}

	private MediaExecutor<Widget> createHTML5MediaExecutor(Media defaultMedia, MediaType mediaType) {
		final HTML5MediaExecutorFactory mediaExecutorFactory = html5MediaExecutorFactoryProvider.get();
		return mediaExecutorFactory.createMediaExecutor(defaultMedia, mediaType);
	}

	private void fireCallback(PlayerEvent event, MediaExecutor<?> defaultMediaExecutor, MediaExecutor<?> fullScreenMediaExecutor) {
		if (event.getSource() instanceof CallbackReceiver) {
			if (fullScreenMediaExecutor == null) {
				((CallbackReceiver) event.getSource()).setCallbackReturnObject(defaultMediaExecutor.getMediaWrapper());
			} else {
				MediaWrappersPair pair = pairFactory.getMediaWrappersPair(defaultMediaExecutor.getMediaWrapper(), fullScreenMediaExecutor.getMediaWrapper());
				((CallbackReceiver) event.getSource()).setCallbackReturnObject(pair);
			}
		}
	}

	private boolean isGeckoSupport(BaseMediaConfiguration bmc) {
		boolean containsOgg = SourceUtil.containsOgg(bmc.getSources());
		return containsOgg || mediaChecker.isHtml5Mp3Supported();
	}

	private MediaExecutor<Widget> createSWFVideoMediaExecutor() {
		VideoExecutorSwf executor = new VideoExecutorSwf();
		executor.setMediaWrapper(new SwfMediaWrapper());
		return executor;
	}

	private MediaExecutor<Widget> createSWFSoundMediaExecutor() {
		SoundExecutorSwf executor = new SoundExecutorSwf();
		executor.setMediaWrapper(new SwfMediaWrapper());
		return executor;
	}

}
