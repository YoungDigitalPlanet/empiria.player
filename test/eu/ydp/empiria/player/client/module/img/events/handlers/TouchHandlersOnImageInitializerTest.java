package eu.ydp.empiria.player.client.module.img.events.handlers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchEndHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchStartHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

@RunWith(MockitoJUnitRunner.class)
public class TouchHandlersOnImageInitializerTest {

	@InjectMocks
	private TouchHandlersOnImageInitializer testObj;
	@Mock
	private TouchToImageEvent touchEventsCoordinates;
	@Mock
	private Widget listenOn;

	@Test
	public void shouldAddTouchMoveHandlerOnImage() {
		// given
		TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);

		// when
		testObj.addTouchOnImageMoveHandler(touchOnImageMoveHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(any(TouchMoveHandlerOnImage.class), eq(TouchMoveEvent.getType()));
	}

	@Test
	public void shouldAddTouchStartHandlerOnImage() {
		// given
		TouchOnImageStartHandler touchOnImageStartHandler = mock(TouchOnImageStartHandler.class);

		// when
		testObj.addTouchOnImageStartHandler(touchOnImageStartHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(any(TouchStartHandlerOnImage.class), eq(TouchStartEvent.getType()));
	}

	@Test
	public void shouldAddTouchEndHandlerOnImage() {
		// given
		TouchOnImageEndHandler touchOnImageEndHandler = mock(TouchOnImageEndHandler.class);

		// when
		testObj.addTouchOnImageEndHandler(touchOnImageEndHandler, listenOn);

		// then
		verify(listenOn).addDomHandler(any(TouchEndHandlerOnImage.class), eq(TouchEndEvent.getType()));
	}
}
