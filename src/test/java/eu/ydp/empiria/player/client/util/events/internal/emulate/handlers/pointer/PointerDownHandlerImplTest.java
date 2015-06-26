package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;

@RunWith(GwtMockitoTestRunner.class)
public class PointerDownHandlerImplTest {

	private PointerDownHandlerImpl testObj;

	@Mock
	private TouchOnStartHandler touchOnStartHandler;

	@Mock
	private PointerDownEvent pointerDownEvent;

	@Mock
	private NativeEvent nativeEvent;

	@Mock
	private PointerEventsCoordinates pointerEventsCoordinates;

	@Before
	public void setUp() {
		testObj = new PointerDownHandlerImpl(touchOnStartHandler, pointerEventsCoordinates);
	}

	@Test
	public void shouldCallOnStart() {
		// given
		when(pointerDownEvent.getNativeEvent()).thenReturn(nativeEvent);
		when(pointerDownEvent.isTouchEvent()).thenReturn(true);

		// when
		testObj.onPointerDown(pointerDownEvent);

		// then
		verify(pointerEventsCoordinates).addEvent(pointerDownEvent);
		verify(touchOnStartHandler).onStart(nativeEvent);
	}

	@Test
	public void shouldntCallOnStart() {
		// given
		when(pointerDownEvent.getNativeEvent()).thenReturn(nativeEvent);
		when(pointerDownEvent.isTouchEvent()).thenReturn(false);

		// when
		testObj.onPointerDown(pointerDownEvent);

		// then
		verify(pointerEventsCoordinates, never()).addEvent(pointerDownEvent);
		verify(touchOnStartHandler, never()).onStart(nativeEvent);
	}
}
