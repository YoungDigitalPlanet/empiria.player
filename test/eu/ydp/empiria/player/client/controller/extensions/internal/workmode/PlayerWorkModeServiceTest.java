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
public class PlayerWorkModeServiceTest {

	@InjectMocks
	private PlayerWorkModeService testObj;

	@Mock(extraInterfaces = { WorkModePreviewClient.class, WorkModeTestClient.class })
	private WorkModeClientType moduleToDisableAndEnable;
	@Mock(extraInterfaces = WorkModeTestClient.class)
	private WorkModeClientType moduleToDisable;
	@Mock(extraInterfaces = WorkModePreviewClient.class)
	private WorkModeClientType moduleToEnable;
	@Mock
	private WorkModeClientType moduleWithoutInterfaces;

	@Test
	public void shouldSetModeOnModuleDuringRegistration_disablingAndEnabling() {
		// given
		PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
		PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

		testObj.tryToUpdateWorkMode(previousWorkMode);
		testObj.tryToUpdateWorkMode(currentWorkMode);

		// when
		testObj.registerModule(moduleToDisableAndEnable);

		// then
		verify((WorkModeTestClient) moduleToDisableAndEnable).disableTestMode();
		verify((WorkModePreviewClient) moduleToDisableAndEnable).enablePreviewMode();
		verifyNoMoreInteractions(moduleToDisableAndEnable);
	}

	@Test
	public void shouldSetModeOnModuleDuringRegistration_disabling() {
		// given
		PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
		PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

		testObj.tryToUpdateWorkMode(previousWorkMode);
		testObj.tryToUpdateWorkMode(currentWorkMode);

		// when
		testObj.registerModule(moduleToDisable);

		// then
		verify((WorkModeTestClient) moduleToDisable).disableTestMode();
		verifyNoMoreInteractions(moduleToDisable);
	}

	@Test
	public void shouldSetModeOnModuleDuringRegistration_enabling() {
		// given
		PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
		PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

		testObj.tryToUpdateWorkMode(previousWorkMode);
		testObj.tryToUpdateWorkMode(currentWorkMode);

		// when
		testObj.registerModule(moduleToEnable);
		testObj.registerModule(moduleWithoutInterfaces);

		// then
		verify((WorkModePreviewClient) moduleToEnable).enablePreviewMode();
		verifyNoMoreInteractions(moduleToEnable);
	}

	@Test
	public void shouldSetModeOnModuleDuringRegistration_() {
		// given
		PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
		PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

		testObj.tryToUpdateWorkMode(previousWorkMode);
		testObj.tryToUpdateWorkMode(currentWorkMode);

		// when
		testObj.registerModule(moduleWithoutInterfaces);

		// then
		verifyZeroInteractions(moduleWithoutInterfaces);
	}

	@Test
	public void shouldNotifyModule_ifTransitionIsValid() {
		// given
		PlayerWorkMode validTransition = PlayerWorkMode.PREVIEW;
		testObj.registerModule(moduleToEnable);

		// when
		testObj.tryToUpdateWorkMode(validTransition);

		// then
		verify((WorkModePreviewClient) moduleToEnable).enablePreviewMode();
	}

	@Test
	public void shouldNotifyModule_ifTransitionIsInvalid() {
		// given
		PlayerWorkMode invalidTransition = PlayerWorkMode.FULL;

		PlayerWorkMode initialState = PlayerWorkMode.TEST;
		testObj.tryToUpdateWorkMode(initialState);
		testObj.registerModule(moduleToDisableAndEnable);

		// when
		testObj.tryToUpdateWorkMode(invalidTransition);

		// then
		verify((WorkModeTestClient) moduleToDisableAndEnable, never()).disableTestMode();
	}

}