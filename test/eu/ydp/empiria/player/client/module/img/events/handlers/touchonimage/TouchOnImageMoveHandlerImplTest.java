package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;

public class TouchOnImageMoveHandlerImplTest {

	private TouchOnImageMoveHandlerImpl testObj;
	private CanvasMoveEvents canvasMoveEvents;

	@Before
	public void setUp() {
		canvasMoveEvents = mock(CanvasMoveEvents.class);
		testObj = new TouchOnImageMoveHandlerImpl(canvasMoveEvents);
	}

	@Test
	public void shouldRunOnMoveMove_whenOneTouch() {
		// given
		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(touchOnImageEvent.getLength()).thenReturn(1);

		Point point = new Point(0, 0);
		when(touchOnImageEvent.getPoint(0)).thenReturn(point);

		// when
		testObj.onMove(touchOnImageEvent);

		// then
		verify(canvasMoveEvents).onMoveMove(point);
	}

	@Test
	public void shouldRunOnMoveScale_whenTwoTouches() {
		// given
		TouchOnImageEvent touchOnImageEvent = mock(TouchOnImageEvent.class);
		when(touchOnImageEvent.getLength()).thenReturn(2);

		Point firstFinger = new Point(0, 0);
		when(touchOnImageEvent.getPoint(0)).thenReturn(firstFinger);

		Point secondFinger = new Point(0, 0);
		when(touchOnImageEvent.getPoint(1)).thenReturn(secondFinger);

		// when
		testObj.onMove(touchOnImageEvent);

		// then
		verify(canvasMoveEvents).onMoveScale(firstFinger, secondFinger);
	}
}
