package eu.ydp.empiria.player.client.module.testmode.submit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.testmode.submit.view.TestPageSubmitView;
import eu.ydp.gwtutil.client.event.factory.Command;

@RunWith(GwtMockitoTestRunner.class)
public class TestPageSubmitPresenterTest {

	// @InjectMocks
	private TestPageSubmitPresenter testObj;

	@Mock
	private TestPageSubmitView testPageSubmitButtonView;
	@Mock
	private FlowRequestInvoker flowRequestInvoker;
	@Mock
	private FlowManager flowManager;

	@Captor
	private ArgumentCaptor<Command> clickCaptor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(flowManager.getFlowRequestInvoker()).thenReturn(flowRequestInvoker);
		testObj = new TestPageSubmitPresenter(testPageSubmitButtonView, flowManager);
	}

	@Test
	public void shouldChangePage() {
		// given
		NativeEvent event = mock(NativeEvent.class);
		testObj.bindUi();
		verify(testPageSubmitButtonView).addHandler(clickCaptor.capture());

		// when
		Command cmd = clickCaptor.getValue();
		cmd.execute(event);

		// then
		verify(flowRequestInvoker).invokeRequest(Mockito.any(FlowRequest.NavigateNextItem.class));
	}

	@Test
	public void shouldLockButton() {
		// given

		// when
		testObj.lock();

		// then
		verify(testPageSubmitButtonView).lock();
	}

	@Test
	public void shouldUnlockButton() {
		// given

		// when
		testObj.unlock();

		// then
		verify(testPageSubmitButtonView).unlock();
	}

	@Test
	public void shouldReturnWidget() {
		// given
		Widget widget = mock(Widget.class);
		when(testPageSubmitButtonView.asWidget()).thenReturn(widget);

		// when
		Widget result = testObj.getView();

		// then
		assertThat(result, is(widget));
	}
}
