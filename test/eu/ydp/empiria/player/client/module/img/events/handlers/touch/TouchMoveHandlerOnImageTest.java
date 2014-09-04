package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gwt.event.dom.client.TouchMoveEvent;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;

public class TouchMoveHandlerOnImageTest {

	private TouchMoveHandlerOnImage testObj;

	@Test
	public void should_run_onMove() {
		// given
		TouchToImageEvent touchToImageEvent = mock(TouchToImageEvent.class);

		TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);

		TouchMoveEvent event = mock(TouchMoveEvent.class);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		testObj = new TouchMoveHandlerOnImage(touchOnImageMoveHandler, touchToImageEvent);

		// when
		testObj.onTouchMove(event);

		// then
		verify(touchOnImageMoveHandler).onMove(touchOnImageEvent);
		verify(event).preventDefault();
	}
}
