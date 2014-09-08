package eu.ydp.empiria.player.client.module.img.events;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(GwtMockitoTestRunner.class)
public class TouchToImageEventTest {

	private final TouchToImageEvent testObj = new TouchToImageEvent();

	@Test
	public void shouldReturnTouchOnImageEvent() {
		// given
		TouchEvent<?> touchEvent = mock(TouchEvent.class);

		Touch touch = mock(Touch.class);

		int x = 1;
		int y = 1;
		when(touch.getClientX()).thenReturn(x);
		when(touch.getClientY()).thenReturn(y);
		JsArray<Touch> array = mock(JsArray.class);
		when(touchEvent.getTouches()).thenReturn(array);
		when(array.get(anyInt())).thenReturn(touch);
		when(array.length()).thenReturn(1);

		// when
		TouchOnImageEvent result = testObj.getTouchOnImageEvent(touchEvent);

		// then
		assertNotNull(result);
		assertEquals(result.getPoint(0), new Point(x, y));
	}
}
