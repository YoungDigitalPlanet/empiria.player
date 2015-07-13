package eu.ydp.empiria.player.client.module.img.events;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.util.position.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class TouchToImageEventTest {

    @InjectMocks
    private TouchToImageEvent testObj;
    @Mock
    private JsArray<Touch> array;
    @Mock
    private TouchEvent<?> touchEvent;
    @Mock
    private Touch touch;

    @Test
    public void shouldReturnTouchOnImageEvent() {
        // given
        int x = 1;
        int y = 1;
        when(touch.getClientX()).thenReturn(x);
        when(touch.getClientY()).thenReturn(y);
        when(touchEvent.getTouches()).thenReturn(array);
        when(array.get(0)).thenReturn(touch);
        when(array.length()).thenReturn(1);

        Point assumedPoint = new Point(x, y);

        // when
        TouchOnImageEvent result = testObj.getTouchOnImageEvent(touchEvent);

        // then
        assertNotNull(result);
        assertEquals(result.getPoint(0), assumedPoint);
    }
}
