package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;

public class TouchOnImageStartHandlerImplTest {

	private TouchOnImageStartHandlerImpl testObj;
	private CanvasMoveEvents canvasMoveEvents;

	@Before
	public void setUp() {
		canvasMoveEvents = mock(CanvasMoveEvents.class);
		testObj = new TouchOnImageStartHandlerImpl(canvasMoveEvents);
	}

	@Test
	public void shouldRunOnMoveStart() {
		// given
		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);

		Point point = new Point(0, 0);
		when(touchOnImageEvent.getPoint(0)).thenReturn(point);

		// when
		testObj.onStart(touchOnImageEvent);

		// then
		verify(canvasMoveEvents).onMoveStart(point);
	}
}
