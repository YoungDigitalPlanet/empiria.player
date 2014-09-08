package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;

public class PointerDownHandlerOnImageTest {

	private final PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);
	private final TouchOnImageStartHandler touchOnStartHandler = mock(TouchOnImageStartHandler.class);
	private final PointerDownHandlerOnImage testObj = new PointerDownHandlerOnImage(touchOnStartHandler, pointerEventsCoordinates);

	@Test
	public void shouldRunOnStart_ifIsTouchEvent() {
		// given
		PointerDownEvent event = mock(PointerDownEvent.class);
		when(event.isTouchEvent()).thenReturn(true);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		// when
		testObj.onPointerDown(event);

		// then
		verify(event).preventDefault();
		verify(pointerEventsCoordinates).addEvent(event);
		verify(touchOnStartHandler).onStart(touchOnImageEvent);
	}

	@Test
	public void shouldNotRunOnStart_ifIsNotTouchEvent() {
		// givens
		PointerDownEvent event = mock(PointerDownEvent.class);
		when(event.isTouchEvent()).thenReturn(false);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		// when
		testObj.onPointerDown(event);

		// then
		verify(event, never()).preventDefault();
		verify(pointerEventsCoordinates, never()).addEvent(event);
		verify(touchOnStartHandler, never()).onStart(touchOnImageEvent);
	}
}
