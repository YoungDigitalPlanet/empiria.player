package eu.ydp.empiria.player.client.controller.extensions.internal.workmode;

import eu.ydp.empiria.player.client.module.workmode.WorkModeClientType;
import eu.ydp.empiria.player.client.module.workmode.WorkModePreviewClient;
import eu.ydp.empiria.player.client.module.workmode.WorkModeTestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerWorkModeNotifierTest {

	@InjectMocks
	private PlayerWorkModeNotifier testObj;

	@Mock(extraInterfaces = { WorkModePreviewClient.class, WorkModeTestClient.class })
	private WorkModeClientType moduleToDisableAndEnable;
	@Mock(extraInterfaces = WorkModeTestClient.class)
	private WorkModeClientType moduleToDisable;
	@Mock(extraInterfaces = WorkModePreviewClient.class)
	private WorkModeClientType moduleToEnable;
	@Mock
	private WorkModeClientType moduleWithoutInterfaces;

	@Test
	public void shouldSwitchModeOnModule() {
		// given
		PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
		PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

		testObj.addModule(moduleToDisableAndEnable);
		testObj.addModule(moduleToDisable);
		testObj.addModule(moduleToEnable);
		testObj.addModule(moduleWithoutInterfaces);

		// when
		testObj.onWorkModeChange(previousWorkMode, currentWorkMode);

		// then
		verify((WorkModePreviewClient) moduleToDisableAndEnable).enablePreviewMode();
		verify((WorkModeTestClient) moduleToDisableAndEnable).disableTestMode();
		verifyNoMoreInteractions(moduleToDisableAndEnable);

		verify((WorkModeTestClient) moduleToDisable).disableTestMode();
		verifyNoMoreInteractions(moduleToDisable);

		verify((WorkModePreviewClient) moduleToEnable).enablePreviewMode();
		verifyNoMoreInteractions(moduleToEnable);

		verifyZeroInteractions(moduleWithoutInterfaces);
	}

}
