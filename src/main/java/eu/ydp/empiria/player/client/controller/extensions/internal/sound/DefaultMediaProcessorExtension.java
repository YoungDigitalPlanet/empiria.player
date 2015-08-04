package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.*;
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
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class DefaultMediaProcessorExtension extends AbstractMediaProcessor {
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
    @Inject
    private Provider<VideoExecutorSwf> videoExecutorSwfProvider;
    @Inject
    private Provider<SoundExecutorSwf> soundExecutorSwfProvider;
    @Inject
    private Provider<SwfMediaWrapper> swfMediaWrapperProvider;
    @Inject
    private Provider<ExternalFullscreenVideoImpl> externalFullscreenVideoProvider;
    @Inject
    private Provider<HTML5AudioImpl> html5AudioProvider;
    @Inject
    private Provider<OldSwfMediaExecutor> oldSwfMediaExecutorProvider;
    @Inject
    private Provider<OldSwfMediaWrapper> oldSwfMediaWrapperProvider;

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
                defaultMedia = externalFullscreenVideoProvider.get();
            } else if (bmc.getMediaType() == MediaType.VIDEO && Video.isSupported() && geckoSupport) {
                defaultMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
                if (bmc.isFullScreenTemplate()) {
                    fullScreenMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
                }
            } else if (Audio.isSupported() && geckoSupport) {
                defaultMedia = html5AudioProvider.get();
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
                    executor = createOldSwfMediaExecutor();
                }
            } else if (defaultMedia == null && UserAgentChecker.isLocal()) {
                executor = createSwfExecutor(bmc);
            } else {
                executor = createHTML5MediaExecutor(defaultMedia, bmc.getMediaType());
                fullScreenExecutor = createHTML5MediaExecutor(fullScreenMedia, bmc.getMediaType());
            }

            initExecutor(executor, bmc);
            initExecutor(fullScreenExecutor, bmc);
            fireCallback(event, executor, fullScreenExecutor);
        }
    }

    private MediaExecutor<Widget> createSwfExecutor(BaseMediaConfiguration bmc) {
        if (bmc.isFeedback()) {
            return simpleSwfExecutorProvider.get();
        } else {
            return createLocalSwfMediaExecutor();
        }
    }

    private MediaExecutor<Widget> createLocalSwfMediaExecutor() {
        LocalSwfMediaExecutor executor = localSwfExecutorProvider.get();
        LocalSwfMediaWrapper mediaWrapper = localSwfWrapperProvider.get();
        executor.setMediaWrapper(mediaWrapper);
        return executor;
    }

    private OldSwfMediaExecutor createOldSwfMediaExecutor() {
        OldSwfMediaExecutor exc = oldSwfMediaExecutorProvider.get();
        exc.setMediaWrapper(oldSwfMediaWrapperProvider.get());
        return exc;
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
        VideoExecutorSwf executor = videoExecutorSwfProvider.get();
        executor.setMediaWrapper(swfMediaWrapperProvider.get());
        return executor;
    }

    private MediaExecutor<Widget> createSWFSoundMediaExecutor() {
        SoundExecutorSwf executor = soundExecutorSwfProvider.get();
        executor.setMediaWrapper(swfMediaWrapperProvider.get());
        return executor;
    }

}
