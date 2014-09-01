package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerDownHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerUpHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerUpEvent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

@RunWith(GwtMockitoTestRunner.class)
public class PointerHandlersInitializerTest {

	@InjectMocks
	private PointerHandlersInitializer testObj;

	@Mock
	private UserAgentUtil userAgentUtil;

	@Mock
	private Widget listenOn;

	@Test
	public void shouldAddTouchMoveHandler_ifIE() {
		// given
		TouchOnMoveHandler touchOnMoveHandler = mock(TouchOnMoveHandler.class);

		// when
		testObj.addTouchMoveHandler(touchOnMoveHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(any(PointerMoveHandlerImpl.class), eq(PointerMoveEvent.getType()));
	}

	@Test
	public void shouldAddTouchStartHandler_ifIE() {
		// given
		TouchOnStartHandler touchOnStartHandler = mock(TouchOnStartHandler.class);

		// when
		testObj.addTouchStartHandler(touchOnStartHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(any(PointerDownHandlerImpl.class), eq(PointerDownEvent.getType()));
	}

	@Test
	public void shouldAddTouchEndHandler_ifIE() {
		// given
		TouchOnEndHandler touchOnStartHandler = mock(TouchOnEndHandler.class);

		// when
		testObj.addTouchEndHandler(touchOnStartHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(any(PointerUpHandlerImpl.class), eq(PointerUpEvent.getType()));
	}
}
