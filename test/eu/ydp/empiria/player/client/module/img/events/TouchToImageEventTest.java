package eu.ydp.empiria.player.client.module.img.events;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(GwtMockitoTestRunner.class)
public class TouchToImageEventTest {

	private TouchToImageEvent touchToImageEvent;

	@Test
	public void should_return_TouchOnImageEvent() {
		// given
		TouchEvent<?> touchEvent = mock(TouchEvent.class);

		com.google.gwt.dom.client.Touch touch = mock(com.google.gwt.dom.client.Touch.class);
		JavaScriptObject js = touch;

		int x = 1;
		int y = 1;
		when(touch.getClientX()).thenReturn(x);
		when(touch.getClientY()).thenReturn(y);
		JsArray<Touch> array = mock(JsArray.class);
		when(touchEvent.getTouches()).thenReturn(array);
		when(array.get(anyInt())).thenReturn(touch);
		when(array.length()).thenReturn(1);

		touchToImageEvent = new TouchToImageEvent();

		// when
		TouchOnImageEvent result = touchToImageEvent.getTouchOnImageEvent(touchEvent);

		// then
		assertNotNull(result);
		assertEquals(result.getPoint(0), new Point(x, y));
	}
}
