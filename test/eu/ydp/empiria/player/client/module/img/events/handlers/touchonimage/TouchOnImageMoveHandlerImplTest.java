package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(GwtMockitoTestRunner.class)
public class TouchOnImageMoveHandlerImplTest {

	@InjectMocks
	private TouchOnImageMoveHandlerImpl testObj;
	@Mock
	private CanvasMoveEvents canvasMoveEvents;
	@Mock
	private TouchOnImageEvent touchOnImageEvent;

	@Test
	public void shouldRunOnMoveMove_whenOneTouch() {
		// given
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
