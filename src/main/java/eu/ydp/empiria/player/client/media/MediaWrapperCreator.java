package eu.ydp.empiria.player.client.media;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.event.DefaultMediaEventController;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MimeSourceProvider;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

import java.util.Map;

public class MediaWrapperCreator {
    @Inject
    private EventsBus eventsBus;
    @Inject
    private Provider<DefaultMediaEventController> defaultMediaEventControllerProvider;
    @Inject
    private MimeSourceProvider mimeSourceProvider;

    public void createMediaWrapper(String src, CallbackReceiver callbackReceiver) {
        Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypeByExtension(src);
        BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, true);
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackReceiver));
    }

    public void createMediaWrapper(Map<String, String> sourcesWithTypes, CallbackReceiver callbackRecevier) {
        BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, true);
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackRecevier));
    }

    public void createSimulationMediaWrapper(String sourcesKey, Map<String, String> sourcesWithTypes, CallbackReceiver callbackRecevier) {
        BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, defaultMediaEventControllerProvider.get());
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackRecevier));
    }

    public void createExternalMediaWrapper(String src, CallbackReceiver callbackRecevier) {
        Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypeByExtension(src);
        BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, defaultMediaEventControllerProvider.get());
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackRecevier));
    }
}
