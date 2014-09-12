package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;

@RunWith(GwtMockitoTestRunner.class)
public class TouchEndHandlerOnImageTest {

	@InjectMocks
	private TouchEndHandlerOnImage testObj;
	@Mock
	private TouchToImageEvent touchToImageEvent;
	@Mock
	private TouchOnImageEndHandler touchOnImageEndHandler;
	@Mock
	private TouchEndEvent event;
	@Mock
	private TouchOnImageEvent touchOnImageEvent;

	@Test
	public void shouldRunOnEnd() {
		// given
		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		// when
		testObj.onTouchEnd(event);

		// then
		verify(touchOnImageEndHandler).onEnd(touchOnImageEvent);
		verify(event).preventDefault();
	}

}
