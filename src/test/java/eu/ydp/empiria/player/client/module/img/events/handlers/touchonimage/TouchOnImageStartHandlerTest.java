package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(MockitoJUnitRunner.class)
public class TouchOnImageStartHandlerTest {

	@InjectMocks
	private TouchOnImageStartHandler testObj;
	@Mock
	private CanvasMoveEvents canvasMoveEvents;
	@Mock
	private TouchOnImageEvent touchOnImageEvent;

	@Test
	public void shouldRunOnMoveStart() {
		// given
		Point point = new Point(0, 0);
		when(touchOnImageEvent.getPoint(0)).thenReturn(point);

		// when
		testObj.onStart(touchOnImageEvent);

		// then
		verify(canvasMoveEvents).onMoveStart(point);
	}
}
