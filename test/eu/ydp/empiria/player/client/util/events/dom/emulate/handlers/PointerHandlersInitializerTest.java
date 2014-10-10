package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerDownHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerUpHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

@RunWith(GwtMockitoTestRunner.class)
public class PointerHandlersInitializerTest {

	@InjectMocks
	private PointerHandlersInitializer testObj;
	@Mock
	private TouchHandlerFactory touchHandlerFactory;
	@Mock
	private Widget listenOn;

	@Test
	public void shouldAddTouchMoveHandler() {
		// given
		TouchOnMoveHandler touchOnMoveHandler = mock(TouchOnMoveHandler.class);
		PointerMoveHandlerImpl pointerMoveHandlerImpl = mock(PointerMoveHandlerImpl.class);
		when(touchHandlerFactory.createPointerMoveHandler(touchOnMoveHandler)).thenReturn(pointerMoveHandlerImpl);

		// when
		testObj.addTouchMoveHandler(touchOnMoveHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(pointerMoveHandlerImpl, PointerMoveEvent.getType());
	}

	@Test
	public void shouldAddTouchStartHandler() {
		// given
		TouchOnStartHandler touchOnStartHandler = mock(TouchOnStartHandler.class);
		PointerDownHandlerImpl pointerDownHandlerImpl = mock(PointerDownHandlerImpl.class);
		when(touchHandlerFactory.createPointerDownHandler(touchOnStartHandler)).thenReturn(pointerDownHandlerImpl);

		// when
		testObj.addTouchStartHandler(touchOnStartHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(pointerDownHandlerImpl, PointerDownEvent.getType());
	}

	@Test
	public void shouldAddTouchEndHandler() {
		// given
		TouchOnEndHandler touchOnEndHandler = mock(TouchOnEndHandler.class);
		PointerUpHandlerImpl pointerUpHandlerImpl = mock(PointerUpHandlerImpl.class);
		when(touchHandlerFactory.createPointerUpHandler(touchOnEndHandler)).thenReturn(pointerUpHandlerImpl);

		// when
		testObj.addTouchEndHandler(touchOnEndHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(pointerUpHandlerImpl, PointerUpEvent.getType());
	}
}
