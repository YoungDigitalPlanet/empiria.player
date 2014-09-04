package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;

public class PointerMoveHandlerOnImageTest {

	private PointerMoveHandlerOnImage testObj;

	@Test
	public void should_run_onStart_if_isTouchEvent() {
		// given
		PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);

		TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);

		PointerMoveEvent event = mock(PointerMoveEvent.class);
		when(event.isTouchEvent()).thenReturn(true);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		testObj = new PointerMoveHandlerOnImage(touchOnImageMoveHandler, pointerEventsCoordinates);
		// when
		testObj.onPointerMove(event);

		// then
		verify(event).preventDefault();
		verify(pointerEventsCoordinates).addEvent(event);
		verify(touchOnImageMoveHandler).onMove(touchOnImageEvent);
	}

	@Test
	public void shouldnt_run_onStart_if_isNotTouchEvent() {
		// given
		PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);

		TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);

		PointerMoveEvent event = mock(PointerMoveEvent.class);
		when(event.isTouchEvent()).thenReturn(false);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		testObj = new PointerMoveHandlerOnImage(touchOnImageMoveHandler, pointerEventsCoordinates);
		// when
		testObj.onPointerMove(event);

		// then
		verify(event, never()).preventDefault();
		verify(pointerEventsCoordinates, never()).addEvent(event);
		verify(touchOnImageMoveHandler, never()).onMove(touchOnImageEvent);
	}
}
