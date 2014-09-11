package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;

public class TouchOnImageEndHandlerImplTest {

	private TouchOnImageEndHandlerImpl testObj;
	private CanvasMoveEvents canvasMoveEvents;

	@Before
	public void setUp() {
		canvasMoveEvents = mock(CanvasMoveEvents.class);
		testObj = new TouchOnImageEndHandlerImpl(canvasMoveEvents);
	}

	@Test
	public void shouldRunOnMoveEnd() {
		// given
		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		// when
		testObj.onEnd(touchOnImageEvent);

		// then
		verify(canvasMoveEvents).onMoveEnd();
	}
}
