package eu.ydp.empiria.player.client.media;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

@RunWith(GwtMockitoTestRunner.class)
public class MediaWrapperCreatorJUnitTest {

	@InjectMocks
	private MediaWrapperCreator testObj;

	@Mock
	private EventsBus eventsBus;
	@Mock
	private CallbackRecevier callbackRecevier;

	@Captor
	private ArgumentCaptor<PlayerEvent> playerEventCaptor;

	private final Map<String, String> sourcesWithTypes = Maps.newHashMap();
	private final String sourcesKey = "file.mp3";

	@Test
	public void shouldCreateDefaultMediaWrapper() {
		// when
		testObj.createMediaWrapper(sourcesKey, sourcesWithTypes, callbackRecevier);

		// then
		verify(eventsBus).fireEvent(playerEventCaptor.capture());
		PlayerEvent capturedEvent = playerEventCaptor.getValue();
		BaseMediaConfiguration capturedConfiguration = (BaseMediaConfiguration) capturedEvent.getValue();
		CallbackRecevier capturedCallback = (CallbackRecevier) capturedEvent.getSource();

		assertThat(capturedEvent.getType()).isEqualTo(PlayerEventTypes.CREATE_MEDIA_WRAPPER);
		assertThat(capturedConfiguration.getSources()).isEqualTo(sourcesWithTypes);
		assertThat(capturedConfiguration.isFeedback()).isEqualTo(true);
		assertThat(capturedCallback).isEqualTo(callbackRecevier);
	}

	@Test
	public void shouldCreateSimulationMediaWrapper() {
		// when
		testObj.createSimulationMediaWrapper(sourcesKey, sourcesWithTypes, callbackRecevier);

		// then
		verify(eventsBus).fireEvent(playerEventCaptor.capture());
		PlayerEvent capturedEvent = playerEventCaptor.getValue();
		BaseMediaConfiguration capturedConfiguration = (BaseMediaConfiguration) capturedEvent.getValue();
		CallbackRecevier capturedCallback = (CallbackRecevier) capturedEvent.getSource();

		assertThat(capturedEvent.getType()).isEqualTo(PlayerEventTypes.CREATE_MEDIA_WRAPPER);
		assertThat(capturedConfiguration.getSources()).isEqualTo(sourcesWithTypes);
		assertThat(capturedConfiguration.getMediaEventControllerOpt().get()).isNotNull();
		assertThat(capturedCallback).isEqualTo(callbackRecevier);
	}
}
