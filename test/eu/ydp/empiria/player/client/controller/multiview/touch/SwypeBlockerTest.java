package eu.ydp.empiria.player.client.controller.multiview.touch;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.user.client.ui.PopupPanel;

import eu.ydp.empiria.player.client.controller.multiview.touch.SwypeBlocker;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxOpenCloseListener;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;

public class SwypeBlockerTest {
	private SwypeBlocker swypeBlocker;

	@Mock
	private TouchController touchController;
	@Mock
	private IsExListBox exListBox;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		swypeBlocker = new SwypeBlocker(touchController);
	}

	@After
	public void after() {
		verifyNoMoreInteractions(touchController);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void addBlockOnOpenCloseHandlerTest_onClose() {
		CloseEvent<PopupPanel> event = mock(CloseEvent.class);
		returnOnClose(event).when(exListBox).addOpenCloseListener(any(ExListBoxOpenCloseListener.class));
		swypeBlocker.addBlockOnOpenCloseHandler(exListBox);

		verify(touchController).setSwypeLock(false);
	}

	@Test
	public void addBlockOnOpenCloseHandlerTest_onOpen() {

		returnOnOpen().when(exListBox).addOpenCloseListener(any(ExListBoxOpenCloseListener.class));
		swypeBlocker.addBlockOnOpenCloseHandler(exListBox);

		verify(touchController).setSwypeLock(true);
	}

	@SuppressWarnings("rawtypes")
	private <T extends CloseEvent> Stubber returnOnClose(final T data) {
		return Mockito.doAnswer(new Answer<T>() {
			@Override
			@SuppressWarnings({ "unchecked" })
			public T answer(InvocationOnMock invocationOnMock) throws Throwable {
				final Object[] args = invocationOnMock.getArguments();
				((ExListBoxOpenCloseListener) args[args.length - 1]).onClose(data);
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private <T extends CloseEvent> Stubber returnOnOpen() {
		return Mockito.doAnswer(new Answer<T>() {
			@Override
			public T answer(InvocationOnMock invocationOnMock) throws Throwable {
				final Object[] args = invocationOnMock.getArguments();
				((ExListBoxOpenCloseListener) args[args.length - 1]).onOpen();
				return null;
			}
		});
	}
}
