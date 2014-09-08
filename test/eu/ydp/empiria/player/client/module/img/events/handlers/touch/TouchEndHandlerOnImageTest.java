package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gwt.event.dom.client.TouchEndEvent;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;

public class TouchEndHandlerOnImageTest {

	private final TouchToImageEvent touchToImageEvent = mock(TouchToImageEvent.class);
	private final TouchOnImageEndHandler touchOnImageEndHandler = mock(TouchOnImageEndHandler.class);
	private final TouchEndHandlerOnImage testObj = new TouchEndHandlerOnImage(touchOnImageEndHandler, touchToImageEvent);

	@Test
	public void shouldRunOnEnd() {
		// given
		TouchEndEvent event = mock(TouchEndEvent.class);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		// when
		testObj.onTouchEnd(event);

		// then
		verify(touchOnImageEndHandler).onEnd(touchOnImageEvent);
		verify(event).preventDefault();
	}

}
