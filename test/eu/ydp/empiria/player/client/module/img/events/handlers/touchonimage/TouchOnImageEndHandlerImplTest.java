package eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.img.events.CanvasMoveEvents;

@RunWith(GwtMockitoTestRunner.class)
public class TouchOnImageEndHandlerImplTest {

	@InjectMocks
	private TouchOnImageEndHandlerImpl testObj;
	@Mock
	private CanvasMoveEvents canvasMoveEvents;
	@Mock
	private TouchOnImageEvent touchOnImageEvent;

	@Test
	public void shouldRunOnMoveEnd() {
		// given

		// when
		testObj.onEnd(touchOnImageEvent);

		// then
		verify(canvasMoveEvents).onMoveEnd();
	}
}
