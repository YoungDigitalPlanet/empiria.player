package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class TouchMoveHandlerImplTest {

    private TouchMoveHandlerImpl testObj;

    @Mock
    private TouchOnMoveHandler touchOnMoveHandler;

    @Mock
    private TouchMoveEvent touchMoveEvent;

    @Mock
    private NativeEvent nativeEvent;

    @Before
    public void setUp() {
        testObj = new TouchMoveHandlerImpl(touchOnMoveHandler);
    }

    @Test
    public void shouldCallOnMove() {
        // given
        when(touchMoveEvent.getNativeEvent()).thenReturn(nativeEvent);

        // when
        testObj.onTouchMove(touchMoveEvent);

        // then
        verify(touchOnMoveHandler).onMove(nativeEvent);
    }
}
