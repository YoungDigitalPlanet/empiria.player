package eu.ydp.empiria.player.client.controller.window;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WindowResizeTimerTest {

    @InjectMocks
    private WindowResizeTimer testObj;
    @Mock
    private EventsBus eventsBus;
    @Captor
    private ArgumentCaptor<PlayerEvent> eventCaptor;

    @Test
    public void shouldExecuteCommand() {
        // given

        // when
        testObj.run();

        // then
        verify(eventsBus).fireAsyncEvent(eventCaptor.capture());
        PlayerEvent playerEvent = eventCaptor.getValue();
        assertThat(playerEvent.getType()).isEqualTo(PlayerEventTypes.WINDOW_RESIZED);
    }
}
