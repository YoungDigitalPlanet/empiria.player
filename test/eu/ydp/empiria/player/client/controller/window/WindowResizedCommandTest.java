package eu.ydp.empiria.player.client.controller.window;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WindowResizedCommandTest {

	@InjectMocks
	private WindowResizedCommand testObj;

	@Mock
	private EventsBus eventsBus;

	@Captor
	private ArgumentCaptor<PlayerEvent> captor;

	@Test
	public void shouldFireAsyncEvent() {
		// given

		// when
		testObj.execute();

		// then
		verify(eventsBus).fireAsyncEvent(captor.capture());
		PlayerEventTypes eventType = captor.getValue().getType();
		assertThat(eventType).isEqualTo(PlayerEventTypes.WINDOW_RESIZED);
	}

}
