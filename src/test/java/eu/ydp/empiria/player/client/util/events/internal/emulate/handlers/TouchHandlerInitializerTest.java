package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchCancelHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchEndHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchStartHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(GwtMockitoTestRunner.class)
public class TouchHandlerInitializerTest {

    @InjectMocks
    private TouchHandlersInitializer testObj;

    @Mock
    private Widget listenOn;

    @Test
    public void shouldAddTouchMoveHandler_ifNotIE() {
        // given
        TouchOnMoveHandler touchOnMoveHandler = mock(TouchOnMoveHandler.class);

        // when
        testObj.addTouchMoveHandler(touchOnMoveHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(any(TouchMoveHandlerImpl.class), eq(TouchMoveEvent.getType()));
    }

    @Test
    public void shouldAddTouchStartHandler_ifNotIE() {
        // given
        TouchOnStartHandler touchOnStartHandler = mock(TouchOnStartHandler.class);

        // when
        testObj.addTouchStartHandler(touchOnStartHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(any(TouchStartHandlerImpl.class), eq(TouchStartEvent.getType()));
    }

    @Test
    public void shouldAddTouchEndHandler_ifNotIE() {
        // given
        TouchOnEndHandler touchOnStartHandler = mock(TouchOnEndHandler.class);

        // when
        testObj.addTouchEndHandler(touchOnStartHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(any(TouchEndHandlerImpl.class), eq(TouchEndEvent.getType()));
    }

    @Test
    public void shouldAddTouchCancelHandler() {
        // given
        TouchOnCancelHandler touchOnStartHandler = mock(TouchOnCancelHandler.class);

        // when
        testObj.addTouchCancelHandler(touchOnStartHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(any(TouchCancelHandlerImpl.class), eq(TouchCancelEvent.getType()));
    }
}
