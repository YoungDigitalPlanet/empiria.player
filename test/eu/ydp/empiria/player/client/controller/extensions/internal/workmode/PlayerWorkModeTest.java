package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.workmode.WorkModeClient;
import eu.ydp.empiria.player.client.module.workmode.WorkModeSwitcher;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode.*;
import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class PlayerWorkModeTest {

	private IModule module = mock(IModule.class, withSettings().extraInterfaces(WorkModeClient.class));

	@Test
	@Parameters(method = "validTransitions")
	public void shouldBeAbleToChangeState(PlayerWorkMode testObj, PlayerWorkMode newState) {
		// when
		boolean actual = testObj.canChangeModeTo(newState);

		// then
		assertThat(actual).isTrue();
	}

	Object[] validTransitions() {
		return $(
				$(FULL, PREVIEW),
				$(FULL, TEST),
				$(FULL, TEST_SUBMITTED),
				$(TEST, PREVIEW),
				$(TEST, TEST_SUBMITTED),
				$(TEST_SUBMITTED, PREVIEW),
				$(TEST_SUBMITTED, TEST)
		);
	}

	@Test
	@Parameters(method = "invalidTransitions")
	public void shouldNotBeAbleToChangeState(PlayerWorkMode testObj, PlayerWorkMode newState) {
		// when
		boolean actual = testObj.canChangeModeTo(newState);

		// then
		assertThat(actual).isFalse();
	}

	Object[] invalidTransitions() {
		return $(
				$(PREVIEW, FULL),
				$(PREVIEW, TEST),
				$(PREVIEW, TEST_SUBMITTED),
				$(TEST, FULL),
				$(TEST_SUBMITTED, FULL)
		);
	}

	@Test
	public void shouldDoNothingOnFullMode() {
		// given
		PlayerWorkMode testObj = FULL;
		WorkModeSwitcher workModeSwitcher = testObj.getWorkModeSwitcher();

		// when
		workModeSwitcher.enable(module);

		// then
		verifyZeroInteractions(module);
	}

	@Test
	public void shouldNotifyClientOnPreviewMode() {
		// given
		PlayerWorkMode testObj = PREVIEW;
		WorkModeSwitcher workModeSwitcher = testObj.getWorkModeSwitcher();

		// when
		workModeSwitcher.enable(module);

		// then
		verify((WorkModeClient) module).enablePreviewMode();
	}

	@Test
	public void shouldNotifyClientOnTestMode() {
		// given
		PlayerWorkMode testObj = TEST;
		WorkModeSwitcher workModeSwitcher = testObj.getWorkModeSwitcher();

		// when
		workModeSwitcher.enable(module);

		// then
		verify((WorkModeClient) module).enableTestMode();
	}

	@Test
	public void shouldNotifyClientOnTestSubmittedMode() {
		// given
		PlayerWorkMode testObj = TEST_SUBMITTED;
		WorkModeSwitcher workModeSwitcher = testObj.getWorkModeSwitcher();

		// when
		workModeSwitcher.enable(module);

		// then
		verify((WorkModeClient) module).enableTestSubmittedMode();
	}
}