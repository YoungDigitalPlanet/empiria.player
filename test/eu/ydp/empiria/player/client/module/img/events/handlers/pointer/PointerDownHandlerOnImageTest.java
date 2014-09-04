package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;

public class PointerDownHandlerOnImageTest {

	private PointerDownHandlerOnImage testObj;

	@Test
	public void should_run_onStart_if_isTouchEvent() {
		// given
		PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);

		TouchOnImageStartHandler touchOnStartHandler = mock(TouchOnImageStartHandler.class);

		PointerDownEvent event = mock(PointerDownEvent.class);
		when(event.isTouchEvent()).thenReturn(true);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		testObj = new PointerDownHandlerOnImage(touchOnStartHandler, pointerEventsCoordinates);
		// when
		testObj.onPointerDown(event);

		// then
		verify(event).preventDefault();
		verify(pointerEventsCoordinates).addEvent(event);
		verify(touchOnStartHandler).onStart(touchOnImageEvent);
	}

	@Test
	public void shouldnt_run_onStart_if_isNotTouchEvent() {
		// given
		PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);

		TouchOnImageStartHandler touchOnStartHandler = mock(TouchOnImageStartHandler.class);

		PointerDownEvent event = mock(PointerDownEvent.class);
		when(event.isTouchEvent()).thenReturn(false);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		testObj = new PointerDownHandlerOnImage(touchOnStartHandler, pointerEventsCoordinates);
		// when
		testObj.onPointerDown(event);

		// then
		verify(event, never()).preventDefault();
		verify(pointerEventsCoordinates, never()).addEvent(event);
		verify(touchOnStartHandler, never()).onStart(touchOnImageEvent);
	}
}
