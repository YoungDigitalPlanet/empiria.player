package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;

public class PointerMoveHandlerOnImageTest {

	private final PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);

	private final TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);

	private final PointerMoveHandlerOnImage testObj = new PointerMoveHandlerOnImage(touchOnImageMoveHandler, pointerEventsCoordinates);

	@Test
	public void shouldRunOnStart_ifIsTouchEvent() {
		// given
		PointerMoveEvent event = mock(PointerMoveEvent.class);
		when(event.isTouchEvent()).thenReturn(true);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		// when
		testObj.onPointerMove(event);

		// then
		verify(event).preventDefault();
		verify(pointerEventsCoordinates).addEvent(event);
		verify(touchOnImageMoveHandler).onMove(touchOnImageEvent);
	}

	@Test
	public void shouldNotRunOnStart_ifIsNotTouchEvent() {
		// given
		PointerMoveEvent event = mock(PointerMoveEvent.class);
		when(event.isTouchEvent()).thenReturn(false);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		// when
		testObj.onPointerMove(event);

		// then
		verify(event, never()).preventDefault();
		verify(pointerEventsCoordinates, never()).addEvent(event);
		verify(touchOnImageMoveHandler, never()).onMove(touchOnImageEvent);
	}
}
