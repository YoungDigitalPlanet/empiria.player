package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerWorkModeServiceTest {

	PlayerWorkModeService testObj = new PlayerWorkModeService();

	@Test
	public void shouldUpdateWorkMode_ifTransitionIsValid() {
		// given
		PlayerWorkMode validTransition = PlayerWorkMode.PREVIEW;

		// when
		testObj.updateWorkMode(validTransition);
		PlayerWorkMode actual = testObj.getCurrentWorkMode();

		// then
		assertThat(actual).isEqualTo(validTransition);
	}

	@Test
	public void shouldNotUpdateWorkMode_ifTransitionIsInvalid() {
		// given
		PlayerWorkMode invalidTransition = PlayerWorkMode.FULL;

		PlayerWorkMode validTransition = PlayerWorkMode.PREVIEW;
		testObj.updateWorkMode(validTransition);

		// when
		testObj.updateWorkMode(invalidTransition);
		PlayerWorkMode actual = testObj.getCurrentWorkMode();

		// then
		assertThat(actual).isEqualTo(validTransition);
	}
}