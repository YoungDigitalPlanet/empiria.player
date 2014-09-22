package eu.ydp.empiria.player.client.controller.data;

import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WorkModeUpdaterForAssessmentTest {

	@InjectMocks
	private WorkModeUpdaterForAssessment testObj;

	@Mock
	private PlayerWorkModeService playerWorkModeService;

	@Test
	public void shouldUpdateModeWhenModeIsCorrect() {
		// given
		String mode = "test";

		// when
		testObj.update(mode);

		// then
		verify(playerWorkModeService).updateWorkMode(PlayerWorkMode.TEST);
	}

	@Test
	public void shouldNotUpdateModeWhenModeIsNotCorrect() {
		// given
		String mode = "not_existing_mode";

		// when
		testObj.update(mode);

		// then
		verify(playerWorkModeService, never()).updateWorkMode(any(PlayerWorkMode.class));
	}
}
