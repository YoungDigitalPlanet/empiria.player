package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerDownHandlerOnImageTest {

    @InjectMocks
    private PointerDownHandlerOnImage testObj;
    @Mock
    private PointerEventsCoordinates pointerEventsCoordinates;
    @Mock
    private TouchOnImageStartHandler touchOnStartHandler;
    @Mock
    private TouchOnImageEvent touchOnImageEvent;
    @Mock
    private PointerDownEvent event;

    @Test
    public void shouldRunOnStart_ifIsTouchEvent() {
        // given
        when(event.isTouchEvent()).thenReturn(true);

        when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

        // when
        testObj.onPointerDown(event);

        // then
        verify(event).preventDefault();
        verify(pointerEventsCoordinates).addEvent(event);
        verify(touchOnStartHandler).onStart(touchOnImageEvent);
    }

    @Test
    public void shouldNotRunOnStart_ifIsNotTouchEvent() {
        // givens
        when(event.isTouchEvent()).thenReturn(false);

        when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

        // when
        testObj.onPointerDown(event);

        // then
        verify(event, never()).preventDefault();
        verify(pointerEventsCoordinates, never()).addEvent(event);
        verify(touchOnStartHandler, never()).onStart(touchOnImageEvent);
    }
}
