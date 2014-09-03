package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;

@RunWith(GwtMockitoTestRunner.class)
public class TouchMoveHandlerImplTest {

	private TouchMoveHandlerImpl testObj;

	@Mock
	private TouchOnMoveHandler touchOnMoveHandler;

	@Mock
	private TouchMoveEvent touchMoveEvent;

	@Before
	public void setUp() {
		testObj = new TouchMoveHandlerImpl(touchOnMoveHandler);
	}

	@Test
	public void shouldCallOnMove() {
		// given

		// when
		testObj.onTouchMove(touchMoveEvent);

		// then
		verify(touchOnMoveHandler).onMove(touchMoveEvent);
	}
}
