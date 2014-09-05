package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;

@RunWith(GwtMockitoTestRunner.class)
public class TouchEndHandlerImplTest {

	private TouchEndHandlerImpl testObj;

	@Mock
	private TouchOnEndHandler touchOnEndHandler;

	@Mock
	private TouchEndEvent touchEndEvent;

	@Mock
	private NativeEvent nativeEvent;

	@Before
	public void setUp() {
		testObj = new TouchEndHandlerImpl(touchOnEndHandler);
	}

	@Test
	public void shouldCallOnMove() {
		// given
		when(touchEndEvent.getNativeEvent()).thenReturn(nativeEvent);

		// when
		testObj.onTouchEnd(touchEndEvent);

		// then
		verify(touchOnEndHandler).onEnd(nativeEvent);
	}
}
