package eu.ydp.empiria.player.client.module.test.reset;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TestResetButtonPresenterTest {

	private TestResetButtonPresenter testObj;

	@Mock
	private TestResetButtonView testResetButtonView;
	@Mock
	private FlowManager flowManager;
	@Mock
	private PlayerWorkModeService playerWorkModeService;

	@Captor
	private ArgumentCaptor<Command> commandCaptor;

	@Before
	public void setUp() {
		testObj = new TestResetButtonPresenter(testResetButtonView, flowManager, playerWorkModeService);
	}

	@Test
	public void shouldGetView() {
		// given
		Widget widget = mock(Widget.class);
		when(testResetButtonView.asWidget()).thenReturn(widget);

		// when
		Widget result = testObj.getView();

		// then
		assertThat(result).isEqualTo(widget);
	}

	@Test
	public void shouldLock() {
		// when
		testObj.lock();

		// then
		testResetButtonView.lock();
	}

	@Test
	public void shouldUnlock() {
		// when
		testObj.unlock();

		// then
		testResetButtonView.unlock();
	}

	@Test
	public void shouldNavigateToFirstItem_ifNoLockWasCalled() {
		// given
		NativeEvent event = mock(NativeEvent.class);

		verify(testResetButtonView).addHandler(commandCaptor.capture());
		Command command = commandCaptor.getValue();

		// when
		command.execute(event);

		// then
		verifyActionsOnResetClick();
	}

	@Test
	public void shouldNavigateToFirstItem_ifLockUnlockWasCalled() {
		// given
		NativeEvent event = mock(NativeEvent.class);
		testObj.lock();

		verify(testResetButtonView).addHandler(commandCaptor.capture());
		Command command = commandCaptor.getValue();

		testObj.unlock();

		// when
		command.execute(event);

		// then
		verifyActionsOnResetClick();
	}

	@Test
	public void shouldNotNavigateToFirstItem() {
		// given
		NativeEvent event = mock(NativeEvent.class);
		testObj.lock();

		verify(testResetButtonView).addHandler(commandCaptor.capture());
		Command command = commandCaptor.getValue();

		// when
		command.execute(event);

		// then
		verifyZeroInteractions(flowManager);
	}

	@Test
	public void shouldEnablePreviewMode() {
		// when
		testObj.enablePreviewMode();

		// then
		verify(testResetButtonView).lock();
		verify(testResetButtonView).enablePreviewMode();
	}

	private void verifyActionsOnResetClick() {
		verify(flowManager).invokeFlowRequest(isA(FlowRequest.NavigateFirstItem.class));
		verify(playerWorkModeService).tryToUpdateWorkMode(PlayerWorkMode.TEST);
	}
}
