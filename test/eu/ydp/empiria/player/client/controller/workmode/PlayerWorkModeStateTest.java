package eu.ydp.empiria.player.client.controller.workmode;

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerWorkModeStateTest {

	@InjectMocks
	private PlayerWorkModeState testObj;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private PlayerWorkModeService playerWorkModeService;
	@Mock
	private JSONStateUtil jsonStateUtil;

	@Captor
	ArgumentCaptor<PlayerEventHandler> eventCaptor;

	@Test
	public void testShouldUpdateModeOnEvent() {
		// given
		JSONArray state = mock(JSONArray.class);

		when(jsonStateUtil.extractString(state)).thenReturn("FULL");

		testObj.postConstruct();
		testObj.setState(state);

		// when
		verify(eventsBus).addHandler(eq(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED)), eventCaptor.capture());
		eventCaptor.getValue().onPlayerEvent(null);

		// then
		verify(playerWorkModeService).tryToUpdateWorkMode(PlayerWorkMode.FULL);
	}

	@Test
	public void shouldReturnStateWithCurrentWorkMode() {
		// given
		final JSONArray EXPECTED = mock(JSONArray.class);

		when(playerWorkModeService.getCurrentWorkMode()).thenReturn(PlayerWorkMode.FULL);
		when(jsonStateUtil.createWithString("FULL")).thenReturn(EXPECTED);

		// when
		JSONArray actual = testObj.getState();

		// then
		assertThat(actual).isEqualTo(EXPECTED);
	}
}