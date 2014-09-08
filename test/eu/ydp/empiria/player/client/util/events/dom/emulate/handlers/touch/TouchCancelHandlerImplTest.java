package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;

@RunWith(GwtMockitoTestRunner.class)
public class TouchCancelHandlerImplTest {

	private TouchCancelHandlerImpl testObj;

	@Mock
	private TouchOnCancelHandler touchOnCancelHandler;

	@Mock
	private TouchCancelEvent touchCancelEvent;

	@Mock
	private NativeEvent nativeEvent;

	@Before
	public void setUp() {
		testObj = new TouchCancelHandlerImpl(touchOnCancelHandler);
	}

	@Test
	public void shouldCallOnMove() {
		// given
		when(touchCancelEvent.getNativeEvent()).thenReturn(nativeEvent);

		// when
		testObj.onTouchCancel(touchCancelEvent);

		// then
		verify(touchOnCancelHandler).onCancel(nativeEvent);
	}
}
