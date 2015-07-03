package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerDownHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerUpHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerHandlersOnImageInitializerTest {

    @InjectMocks
    private PointerHandlersOnImageInitializer testObj;
    @Mock
    private PointerEventsCoordinates pointerEventsCoordinates;
    @Mock
    private Widget listenOn;
    @Mock
    private TouchHandlerFactory touchHandlerFactory;

    @Test
    public void shouldAddPointerMoveHandlerOnImage() {
        // given
        TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);
        PointerMoveHandlerOnImage pointerMoveHandlerOnImage = mock(PointerMoveHandlerOnImage.class);
        when(touchHandlerFactory.createPointerMoveHandlerOnImage(touchOnImageMoveHandler)).thenReturn(pointerMoveHandlerOnImage);

        // when
        testObj.addTouchOnImageMoveHandler(touchOnImageMoveHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerMoveHandlerOnImage, PointerMoveEvent.getType());
    }

    @Test
    public void shouldAddPointerDownHandlerOnImage() {
        // given
        TouchOnImageStartHandler touchOnImageStartHandler = mock(TouchOnImageStartHandler.class);
        PointerDownHandlerOnImage pointerDownHandlerOnImage = mock(PointerDownHandlerOnImage.class);
        when(touchHandlerFactory.createPointerDownHandlerOnImage(touchOnImageStartHandler)).thenReturn(pointerDownHandlerOnImage);

        // when
        testObj.addTouchOnImageStartHandler(touchOnImageStartHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerDownHandlerOnImage, PointerDownEvent.getType());
    }

    @Test
    public void shouldAddPointerUpHandlerOnImage() {
        // given
        TouchOnImageEndHandler touchOnImageEndHandler = mock(TouchOnImageEndHandler.class);
        PointerUpHandlerOnImage pointerUpHandlerOnImage = mock(PointerUpHandlerOnImage.class);
        when(touchHandlerFactory.createPointerUpHandlerOnImage(touchOnImageEndHandler)).thenReturn(pointerUpHandlerOnImage);

        // when
        testObj.addTouchOnImageEndHandler(touchOnImageEndHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerUpHandlerOnImage, PointerUpEvent.getType());
    }
}
