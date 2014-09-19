package eu.ydp.empiria.player.client.module.test.reset;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.gwtutil.client.event.factory.Command;

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
	public void shouldEnable() {
		// when
		testObj.enable();

		// then
		testResetButtonView.lock();
	}

	@Test
	public void shouldDisable() {
		// when
		testObj.disable();

		// then
		testResetButtonView.unlock();
	}

	@Test
	public void shouldNavigateToFirstItem_ifNoDisableWasCalled() {
		// given
		NativeEvent event = mock(NativeEvent.class);
		testObj.bindUi();

		verify(testResetButtonView).addHandler(commandCaptor.capture());
		Command command = commandCaptor.getValue();

		// when
		command.execute(event);

		// then
		verifyActionsOnResetClick();
	}

	@Test
	public void shouldNavigateToFirstItem_ifEnableDisableWasCalled() {
		// given
		NativeEvent event = mock(NativeEvent.class);
		testObj.bindUi();
		testObj.enable();

		verify(testResetButtonView).addHandler(commandCaptor.capture());
		Command command = commandCaptor.getValue();

		testObj.disable();

		// when
		command.execute(event);

		// then
		verifyActionsOnResetClick();
	}

	@Test
	public void shouldNotNavigateToFirstItem() {
		// given
		NativeEvent event = mock(NativeEvent.class);
		testObj.bindUi();
		testObj.enable();

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
		verify(flowManager).invokeFlowRequest(any(FlowRequest.NavigateFirstItem.class));
		verify(playerWorkModeService).updateWorkMode(PlayerWorkMode.TEST_SUBMITTED);
	}
}
