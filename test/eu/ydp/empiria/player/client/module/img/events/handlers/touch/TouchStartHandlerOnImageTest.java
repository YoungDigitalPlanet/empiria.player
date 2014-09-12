package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

@RunWith(GwtMockitoTestRunner.class)
public class TouchStartHandlerOnImageTest {

	@InjectMocks
	private TouchStartHandlerOnImage testObj;
	@Mock
	private TouchToImageEvent touchToImageEvent;
	@Mock
	private TouchOnImageStartHandler touchOnImageStartHandler;
	@Mock
	private TouchStartEvent event;
	@Mock
	private TouchOnImageEvent touchOnImageEvent;

	@Test
	public void shouldRunOnStart() {
		// given
		when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

		// when
		testObj.onTouchStart(event);

		// then
		verify(touchOnImageStartHandler).onStart(touchOnImageEvent);
		verify(event).preventDefault();
	}
}
