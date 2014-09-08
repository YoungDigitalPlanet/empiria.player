package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gwt.event.dom.client.TouchStartEvent;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

public class TouchStartHandlerOnImageTest {

	private final TouchToImageEvent touchToImageEvent = mock(TouchToImageEvent.class);

	private final TouchOnImageStartHandler touchOnImageStartHandler = mock(TouchOnImageStartHandler.class);

	private final TouchStartHandlerOnImage testObj = new TouchStartHandlerOnImage(touchOnImageStartHandler, touchToImageEvent);

	@Test
	public void shouldRunOnStart() {
		// given
		TouchStartEvent event = mock(TouchStartEvent.class);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		// when
		testObj.onTouchStart(event);

		// then
		verify(touchOnImageStartHandler).onStart(touchOnImageEvent);
		verify(event).preventDefault();
	}
}
