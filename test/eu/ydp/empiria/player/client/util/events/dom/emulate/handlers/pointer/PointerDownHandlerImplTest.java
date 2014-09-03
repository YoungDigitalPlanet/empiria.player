package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;

@RunWith(GwtMockitoTestRunner.class)
public class PointerDownHandlerImplTest {

	private PointerDownHandlerImpl testObj;

	@Mock
	private TouchOnStartHandler touchOnStartHandler;

	@Mock
	private PointerDownEvent pointerDownEvent;

	@Before
	public void setUp() {
		testObj = new PointerDownHandlerImpl(touchOnStartHandler);
	}

	@Test
	public void shouldCallOnStart() {
		// given
		when(pointerDownEvent.isTouchEvent()).thenReturn(true);

		// when
		testObj.onPointerDown(pointerDownEvent);

		// then
		verify(touchOnStartHandler).onStart(pointerDownEvent);
	}

	@Test
	public void shouldntCallOnStart() {
		// given
		when(pointerDownEvent.isTouchEvent()).thenReturn(false);

		// when
		testObj.onPointerDown(pointerDownEvent);

		// then
		verify(touchOnStartHandler, never()).onStart(pointerDownEvent);
	}
}
