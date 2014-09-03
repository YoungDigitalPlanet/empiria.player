package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

@RunWith(GwtMockitoTestRunner.class)
public class TouchStartHandlerImplTest {

	private TouchStartHandlerImpl testObj;

	@Mock
	private TouchOnStartHandler touchOnStartHandler;

	@Mock
	private TouchStartEvent touchStartEvent;

	@Before
	public void setUp() {
		testObj = new TouchStartHandlerImpl(touchOnStartHandler);
	}

	@Test
	public void shouldCallOnMove() {
		// given

		// when
		testObj.onTouchStart(touchStartEvent);

		// then
		verify(touchOnStartHandler).onStart(touchStartEvent);
	}
}
