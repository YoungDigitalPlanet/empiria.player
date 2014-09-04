package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gwt.event.dom.client.TouchEndEvent;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;

public class TouchEndHandlerOnImageTest {

	private TouchEndHandlerOnImage testObj;

	@Test
	public void should_run_onEnd() {
		// given
		TouchToImageEvent touchToImageEvent = mock(TouchToImageEvent.class);

		TouchOnImageEndHandler touchOnImageEndHandler = mock(TouchOnImageEndHandler.class);

		TouchEndEvent event = mock(TouchEndEvent.class);

		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		testObj = new TouchEndHandlerOnImage(touchOnImageEndHandler, touchToImageEvent);

		// when
		testObj.onTouchEnd(event);

		// then
		verify(touchOnImageEndHandler).onEnd(touchOnImageEvent);
		verify(event).preventDefault();
	}

}
