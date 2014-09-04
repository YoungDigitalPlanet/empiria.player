package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gwt.event.dom.client.TouchStartEvent;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

public class TouchStartHandlerOnImageTest {

	private TouchStartHandlerOnImage testObj;

	@Test
	public void should_run_oStart() {
		// given
		TouchToImageEvent touchToImageEvent = mock(TouchToImageEvent.class);

		TouchOnImageStartHandler touchOnImageStartHandler = mock(TouchOnImageStartHandler.class);

		TouchStartEvent event = mock(TouchStartEvent.class);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		testObj = new TouchStartHandlerOnImage(touchOnImageStartHandler, touchToImageEvent);

		// when
		testObj.onTouchStart(event);

		// then
		verify(touchOnImageStartHandler).onStart(touchOnImageEvent);
		verify(event).preventDefault();
	}

}
