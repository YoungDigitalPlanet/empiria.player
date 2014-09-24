package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerWorkModeServiceTest {

	PlayerWorkModeService testObj = new PlayerWorkModeService();

	@Test
	public void shouldUpdateWorkMode_ifTransitionIsValid() {
		// given
		PlayerWorkMode validTransition = PlayerWorkMode.PREVIEW;

		// when
		testObj.tryToUpdateWorkMode(validTransition);
		PlayerWorkMode actual = testObj.getCurrentWorkMode();

		// then
		assertThat(actual).isEqualTo(validTransition);
	}

	@Test
	public void shouldNotUpdateWorkMode_ifTransitionIsInvalid() {
		// given
		PlayerWorkMode invalidTransition = PlayerWorkMode.FULL;

		PlayerWorkMode initialState = PlayerWorkMode.PREVIEW;
		testObj.tryToUpdateWorkMode(initialState);

		// when
		testObj.tryToUpdateWorkMode(invalidTransition);
		PlayerWorkMode actual = testObj.getCurrentWorkMode();

		// then
		assertThat(actual).isEqualTo(initialState);
	}

	@Test
	public void shouldReturnPreviousState_whenTransitionIsValid() {
		// given
		PlayerWorkMode validTransition = PlayerWorkMode.PREVIEW;

		// when
		testObj.tryToUpdateWorkMode(validTransition);
		Optional<PlayerWorkMode> actual = testObj.getPreviousWorkMode();

		// then
		assertThat(actual.isPresent()).isTrue();
		assertThat(actual.get()).isEqualTo(PlayerWorkMode.FULL);
	}

	@Test
	public void shouldNotReturnPreviousState_onStart() {
		// when
		Optional<PlayerWorkMode> actual = testObj.getPreviousWorkMode();

		// then
		assertThat(actual.isPresent()).isFalse();
	}

	@Test
	public void shouldNotReturnPreviousState_ifTransitionIsInvalid() {
		// when
		PlayerWorkMode invalidTransition = PlayerWorkMode.TEST;

		PlayerWorkMode initialState = PlayerWorkMode.PREVIEW;
		testObj.tryToUpdateWorkMode(initialState);

		// when
		testObj.tryToUpdateWorkMode(invalidTransition);
		Optional<PlayerWorkMode> actual = testObj.getPreviousWorkMode();

		// then
		assertThat(actual.get()).isNotEqualTo(invalidTransition);
	}
}