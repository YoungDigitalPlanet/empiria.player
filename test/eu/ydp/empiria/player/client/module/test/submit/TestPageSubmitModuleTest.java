package eu.ydp.empiria.player.client.module.test.submit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;

@RunWith(GwtMockitoTestRunner.class)
public class TestPageSubmitModuleTest {

	@InjectMocks
	private TestPageSubmitModule testObj;

	@Mock
	private TestPageSubmitPresenter presenter;

	@Test
	public void shouldReturnWidget() {
		// given
		Widget widget = mock(Widget.class);
		when(presenter.getView()).thenReturn(widget);

		// when
		Widget result = testObj.getView();

		// then
		assertThat(result, is(widget));
	}

	@Test
	public void shouldLockPresenter() {
		// given

		// when
		testObj.lock(true);

		// then
		verify(presenter).lock();
	}

	@Test
	public void shouldUnlockPresenter() {
		// given

		// when
		testObj.lock(false);

		// then
		verify(presenter).unlock();
	}

	@Test
	public void shouldEnableTestSubmittedMode() {
		// given

		// when
		testObj.enableTestSubmittedMode();
		// then
		verify(presenter).enableTestSubmittedMode();
	}

	@Test
	public void shouldDisableTestSubmittedMode() {
		// given

		// when
		testObj.disableTestSubmittedMode();

		// then
		verify(presenter).disableTestSubmittedMode();
	}

	@Test
	public void shouldEnablePreviewMode() {
		// given

		// when
		testObj.enablePreviewMode();
		// then
		verify(presenter).enablePreviewMode();
	}
}
