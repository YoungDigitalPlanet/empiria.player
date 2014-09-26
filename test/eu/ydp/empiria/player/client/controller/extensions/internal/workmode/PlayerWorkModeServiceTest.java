package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PlayerWorkModeServiceTest {

	@InjectMocks
	private PlayerWorkModeService testObj;

	@Mock
	private PlayerWorkModeNotifier playerWorkModeNotifier;

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
		PlayerWorkMode actual = testObj.getPreviousWorkMode();

		// then
		assertThat(actual).isEqualTo(PlayerWorkMode.FULL);
	}

	@Test
	public void shouldFullAsPreviousStateReturnPreviousState_onStart() {
		// when
		PlayerWorkMode actual = testObj.getPreviousWorkMode();

		// then
		assertThat(actual).isEqualTo(PlayerWorkMode.FULL);
	}

	@Test
	public void shouldNotReturnPreviousState_ifTransitionIsInvalid() {
		// when
		PlayerWorkMode invalidTransition = PlayerWorkMode.TEST_SUBMITTED;

		PlayerWorkMode firstTransition = PlayerWorkMode.TEST;
		testObj.tryToUpdateWorkMode(firstTransition);

		PlayerWorkMode secondTransition = PlayerWorkMode.PREVIEW;
		testObj.tryToUpdateWorkMode(secondTransition);

		// when
		testObj.tryToUpdateWorkMode(invalidTransition);
		PlayerWorkMode actual = testObj.getPreviousWorkMode();

		// then
		assertThat(actual).isNotEqualTo(invalidTransition);
	}
}