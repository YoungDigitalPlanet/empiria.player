package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;

@RunWith(GwtMockitoTestRunner.class)
public class PointerUpHandlerImplTest {

	private PointerUpHandlerImpl testObj;

	@Mock
	private TouchOnEndHandler touchOnEndHandler;

	@Mock
	private PointerUpEvent pointerUpEvent;

	@Mock
	private NativeEvent nativeEvent;

	@Before
	public void setUp() {
		testObj = new PointerUpHandlerImpl(touchOnEndHandler);
	}

	@Test
	public void shouldCallOnStart() {
		// given
		when(pointerUpEvent.getNativeEvent()).thenReturn(nativeEvent);
		when(pointerUpEvent.isTouchEvent()).thenReturn(true);

		// when
		testObj.onPointerUp(pointerUpEvent);

		// then
		verify(touchOnEndHandler).onEnd(nativeEvent);
	}

	@Test
	public void shouldntCallOnStart() {
		// given
		when(pointerUpEvent.getNativeEvent()).thenReturn(nativeEvent);
		when(pointerUpEvent.isTouchEvent()).thenReturn(false);

		// when
		testObj.onPointerUp(pointerUpEvent);

		// then
		verify(touchOnEndHandler, never()).onEnd(nativeEvent);
	}
}
