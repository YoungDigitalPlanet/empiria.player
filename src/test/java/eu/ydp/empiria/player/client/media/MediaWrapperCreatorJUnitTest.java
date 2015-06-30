package eu.ydp.empiria.player.client.media;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.event.SimulationMediaEventController;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MimeSourceProvider;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class MediaWrapperCreatorJUnitTest {

    @InjectMocks
    private MediaWrapperCreator testObj;

    @Mock
    private EventsBus eventsBus;
    @Mock
    private Provider<SimulationMediaEventController> simulationMediaEventControllerProvider;

    @Mock
    private CallbackReceiver callbackReceiver;
    @Mock
    private MimeSourceProvider mimeSourceProvider;

    @Captor
    private ArgumentCaptor<PlayerEvent> playerEventCaptor;

    private final Map<String, String> sourcesWithTypes = Maps.newHashMap();
    private final String sourcesKey = "file.mp3";

    @Test
    public void shouldCreateMediaWrapper() {
        // given
        when(mimeSourceProvider.getSourcesWithTypeByExtension(sourcesKey)).thenReturn(sourcesWithTypes);

        // when
        testObj.createMediaWrapper(sourcesKey, callbackReceiver);

        // then
        verify(eventsBus).fireEvent(playerEventCaptor.capture());

        PlayerEvent capturedEvent = playerEventCaptor.getValue();

        BaseMediaConfiguration capturedConfiguration = (BaseMediaConfiguration) capturedEvent.getValue();
        CallbackReceiver capturedCallback = (CallbackReceiver) capturedEvent.getSource();

        assertThat(capturedEvent.getType()).isEqualTo(PlayerEventTypes.CREATE_MEDIA_WRAPPER);
        assertThat(capturedConfiguration.getSources()).isEqualTo(sourcesWithTypes);
        assertThat(capturedConfiguration.isFeedback()).isEqualTo(true);
        assertThat(capturedCallback).isEqualTo(callbackReceiver);
    }

    @Test
    public void shouldCreateDefaultMediaWrapper() {
        // when
        testObj.createMediaWrapper(sourcesKey, sourcesWithTypes, callbackReceiver);

        // then
        verify(eventsBus).fireEvent(playerEventCaptor.capture());
        PlayerEvent capturedEvent = playerEventCaptor.getValue();
        BaseMediaConfiguration capturedConfiguration = (BaseMediaConfiguration) capturedEvent.getValue();
        CallbackReceiver capturedCallback = (CallbackReceiver) capturedEvent.getSource();

        assertThat(capturedEvent.getType()).isEqualTo(PlayerEventTypes.CREATE_MEDIA_WRAPPER);
        assertThat(capturedConfiguration.getSources()).isEqualTo(sourcesWithTypes);
        assertThat(capturedConfiguration.isFeedback()).isEqualTo(true);
        assertThat(capturedCallback).isEqualTo(callbackReceiver);
    }

    @Test
    public void shouldCreateSimulationMediaWrapper() {
        // given
        when(simulationMediaEventControllerProvider.get()).thenReturn(mock(SimulationMediaEventController.class));

        // when
        testObj.createSimulationMediaWrapper(sourcesKey, sourcesWithTypes, callbackReceiver);

        // then
        verify(eventsBus).fireEvent(playerEventCaptor.capture());
        PlayerEvent capturedEvent = playerEventCaptor.getValue();
        BaseMediaConfiguration capturedConfiguration = (BaseMediaConfiguration) capturedEvent.getValue();
        CallbackReceiver capturedCallback = (CallbackReceiver) capturedEvent.getSource();

        assertThat(capturedEvent.getType()).isEqualTo(PlayerEventTypes.CREATE_MEDIA_WRAPPER);
        assertThat(capturedConfiguration.getSources()).isEqualTo(sourcesWithTypes);
        assertThat(capturedConfiguration.getMediaEventControllerOpt().get()).isNotNull();
        assertThat(capturedCallback).isEqualTo(callbackReceiver);
    }
}
