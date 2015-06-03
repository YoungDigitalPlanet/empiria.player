package eu.ydp.empiria.player.client.controller.workmode;

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.json.JSONStateSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	private PlayerWorkModeService playerWorkModeService;
	@Mock
	private JSONStateSerializer jsonStateUtil;

	@Test
	public void testShouldUpdateModeOnService() {
		// given
		JSONArray state = mock(JSONArray.class);
		when(jsonStateUtil.extractString(state)).thenReturn("FULL");

		// when
		testObj.setState(state);

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