package eu.ydp.empiria.player.client.controller.window;

import static org.mockito.Mockito.*;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class WindowResizeControllerTest {

	@InjectMocks
	private WindowResizeController testObj;
	@Mock
	private CommandTimer commandTimer;
	@Mock
	private WindowResizedCommand command;
	
	@Test
	public void shouldInitCommandTimer() {
		// given
		
		// when

		// then
		verify(commandTimer).setCommand(command);
	}
	
	@Test
	public void shouldScheduleTimer() {
		// given
		ResizeEvent event = mock(ResizeEvent.class);
		int delayMillis = 250;

		// when
		testObj.onResize(event);

		// then
		verify(commandTimer).schedule(delayMillis);
	}
}
