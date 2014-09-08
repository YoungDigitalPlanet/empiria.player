package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import static org.mockito.Mockito.*;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;

public class PointerUpHandlerOnImageTest {

	private final PointerEventsCoordinates pointerEventsCoordinates = mock(PointerEventsCoordinates.class);
	private final TouchOnImageEndHandler touchOnStartHandler = mock(TouchOnImageEndHandler.class);
	private final PointerUpHandlerOnImage testObj = new PointerUpHandlerOnImage(touchOnStartHandler, pointerEventsCoordinates);

	@Test
	public void shouldRunOnEnd_ifIsTouchEvent() {
		// given
		PointerUpEvent event = mock(PointerUpEvent.class);
		when(event.isTouchEvent()).thenReturn(true);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		// when
		testObj.onPointerUp(event);

		// then
		verify(event).preventDefault();
		verify(pointerEventsCoordinates).removeEvent(event);
		verify(touchOnStartHandler).onEnd(touchOnImageEvent);
	}

	@Test
	public void shouldNotRunOnEnd_ifIsNotTouchEvent() {
		// given
		PointerUpEvent event = mock(PointerUpEvent.class);
		when(event.isTouchEvent()).thenReturn(false);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

		// when
		testObj.onPointerUp(event);

		// then
		verify(event, never()).preventDefault();
		verify(pointerEventsCoordinates, never()).addEvent(event);
		verify(touchOnStartHandler, never()).onEnd(touchOnImageEvent);
	}
}
