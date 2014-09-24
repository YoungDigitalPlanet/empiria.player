package eu.ydp.empiria.player.client.module.report;

import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WorkModeSwitcherForReportTest {

	@InjectMocks
	private WorkModeSwitcherForReport testObj;

	@Mock
	private PlayerWorkModeService playerWorkModeService;

	@Test
	public void shouldSwitchToTestSubmittedWorkModeIfCurrentIsTest() {
		// given
		when(playerWorkModeService.getCurrentWorkMode()).thenReturn(PlayerWorkMode.TEST);

		// when
		testObj.switchIfNeed();

		// then
		verify(playerWorkModeService).updateWorkMode(PlayerWorkMode.TEST_SUBMITTED);
	}

	@Test
	public void shouldNotSwitchWorkModeIfCurrentIsNotTest() {
		// given
		when(playerWorkModeService.getCurrentWorkMode()).thenReturn(PlayerWorkMode.FULL);

		// when
		testObj.switchIfNeed();

		// then
		verify(playerWorkModeService, never()).updateWorkMode(any(PlayerWorkMode.class));
	}
}
