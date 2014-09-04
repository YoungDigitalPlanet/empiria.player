package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;

public class PointerUpHandlerOnImageTest {

	private PointerUpHandlerOnImage testObj;

	@Test
	public void should_run_onEnd_if_isTouchEvent() {
		// given
		PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);

		TouchOnImageEndHandler touchOnStartHandler = mock(TouchOnImageEndHandler.class);

		PointerUpEvent event = mock(PointerUpEvent.class);
		when(event.isTouchEvent()).thenReturn(true);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		testObj = new PointerUpHandlerOnImage(touchOnStartHandler, pointerEventsCoordinates);
		// when
		testObj.onPointerUp(event);

		// then
		verify(event).preventDefault();
		verify(pointerEventsCoordinates).removeEvent(event);
		verify(touchOnStartHandler).onEnd(touchOnImageEvent);
	}

	@Test
	public void shouldnt_run_onEnd_if_isNotTouchEvent() {
		// given
		PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);

		TouchOnImageEndHandler touchOnStartHandler = mock(TouchOnImageEndHandler.class);

		PointerUpEvent event = mock(PointerUpEvent.class);
		when(event.isTouchEvent()).thenReturn(false);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		testObj = new PointerUpHandlerOnImage(touchOnStartHandler, pointerEventsCoordinates);
		// when
		testObj.onPointerUp(event);

		// then
		verify(event, never()).preventDefault();
		verify(pointerEventsCoordinates, never()).addEvent(event);
		verify(touchOnStartHandler, never()).onEnd(touchOnImageEvent);
	}
}
