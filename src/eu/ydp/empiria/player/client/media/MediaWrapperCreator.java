package eu.ydp.empiria.player.client.media;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.event.SimulationMediaEventController;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class MediaWrapperCreator {
	@Inject
	private EventsBus eventsBus;
	@Inject
	private Provider<SimulationMediaEventController> simulationMediaEventControllerProvider;

	public void createMediaWrapper(String sourcesKey, Map<String, String> sourcesWithTypes, CallbackReceiver callbackRecevier) {
		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, true);
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackRecevier));
	}

	public void createSimulationMediaWrapper(String sourcesKey, Map<String, String> sourcesWithTypes, CallbackReceiver callbackRecevier) {
		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, simulationMediaEventControllerProvider.get());
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackRecevier));
	}
}
