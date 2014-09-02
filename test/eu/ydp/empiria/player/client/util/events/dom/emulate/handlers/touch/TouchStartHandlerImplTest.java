package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwt.dom.client.NativeEvent;
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

	@Mock
	private NativeEvent nativeEvent;

	@Before
	public void setUp() {
		testObj = new TouchStartHandlerImpl(touchOnStartHandler);
	}

	@Test
	public void shouldCallOnMove() {
		// given
		when(touchStartEvent.getNativeEvent()).thenReturn(nativeEvent);

		// when
		testObj.onTouchStart(touchStartEvent);

		// then
		verify(touchOnStartHandler).onStart(nativeEvent);
	}
}
