package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;

@RunWith(GwtMockitoTestRunner.class)
public class PointerMoveHandlerImplTest {
	private PointerMoveHandlerImpl testObj;

	@Mock
	private TouchOnMoveHandler touchMoveHandler;

	@Mock
	private PointerMoveEvent pointerMoveEvent;

	@Before
	public void setUp() {
		testObj = new PointerMoveHandlerImpl(touchMoveHandler);
	}

	@Test
	public void shouldCallOnStart() {
		// given
		when(pointerMoveEvent.isTouchEvent()).thenReturn(true);

		// when
		testObj.onPointerMove(pointerMoveEvent);

		// then
		verify(touchMoveHandler).onMove(pointerMoveEvent);
	}

	@Test
	public void shouldntCallOnStart() {
		// given
		when(pointerMoveEvent.isTouchEvent()).thenReturn(false);

		// when
		testObj.onPointerMove(pointerMoveEvent);

		// then
		verify(touchMoveHandler, never()).onMove(pointerMoveEvent);
	}
}
