package eu.ydp.empiria.player.client.module.img.events.handlers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerDownHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerUpHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;

@RunWith(MockitoJUnitRunner.class)
public class PointerHandlersOnImageInitializerTest {

	@InjectMocks
	private PointerHandlersOnImageInitializer testObj;
	@Mock
	private PointerEventsCoordinates pointerEventsCoordinates;
	@Mock
	private Widget listenOn;

	@Test
	public void shouldAddPointerMoveHandlerOnImage() {
		// given
		TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);

		// when
		testObj.addTouchOnImageMoveHandler(touchOnImageMoveHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(isA(PointerMoveHandlerOnImage.class), eq(PointerMoveEvent.getType()));
	}

	@Test
	public void shouldAddPointerDownHandlerOnImage() {
		// given
		TouchOnImageStartHandler touchOnImageStartHandler = mock(TouchOnImageStartHandler.class);

		// when
		testObj.addTouchOnImageStartHandler(touchOnImageStartHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(isA(PointerDownHandlerOnImage.class), eq(PointerDownEvent.getType()));
	}

	@Test
	public void shouldAddPointerUpHandlerOnImage() {
		// given
		TouchOnImageEndHandler touchOnImageEndHandler = mock(TouchOnImageEndHandler.class);

		// when
		testObj.addTouchOnImageEndHandler(touchOnImageEndHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(isA(PointerUpHandlerOnImage.class), eq(PointerUpEvent.getType()));
	}
}
