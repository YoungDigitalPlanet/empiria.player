package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gwt.event.dom.client.TouchMoveEvent;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;

public class TouchMoveHandlerOnImageTest {

	private final TouchToImageEvent touchToImageEvent = mock(TouchToImageEvent.class);

	private final TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);

	private final TouchMoveHandlerOnImage testObj = new TouchMoveHandlerOnImage(touchOnImageMoveHandler, touchToImageEvent);

	@Test
	public void shouldRunOnMove() {
		// given
		TouchMoveEvent event = mock(TouchMoveEvent.class);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		// when
		testObj.onTouchMove(event);

		// then
		verify(touchOnImageMoveHandler).onMove(touchOnImageEvent);
		verify(event).preventDefault();
	}
}
