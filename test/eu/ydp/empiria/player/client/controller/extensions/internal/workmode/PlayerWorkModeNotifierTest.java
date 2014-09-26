package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.module.workmode.WorkModeClientType;
import eu.ydp.empiria.player.client.module.workmode.WorkModeSwitcher;

public class PlayerWorkModeNotifierTest {

	private final PlayerWorkModeNotifier testObj = new PlayerWorkModeNotifier();

	@Test
	public void shouldSwitchModeOnModule() {
		// given
		Optional<PlayerWorkMode> previousWorkMode = Optional.absent();
		PlayerWorkMode currentWorkMode = mock(PlayerWorkMode.class);
		WorkModeSwitcher currentWorkModeSwitcher = mock(WorkModeSwitcher.class);
		when(currentWorkMode.getWorkModeSwitcher()).thenReturn(currentWorkModeSwitcher);

		WorkModeClientType module = mock(WorkModeClientType.class);
		testObj.addModule(module);

		// when
		testObj.onWorkModeChange(previousWorkMode, currentWorkMode);

		// then
		verify(currentWorkModeSwitcher).enable(module);

	}

}
