package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.mockito.Mockito.*;

import com.google.gwt.user.client.Command;
import org.junit.Test;

public class SlideshowTimerTest {

	private final SlideshowTimer testObj = new SlideshowTimer();

	@Test
	public void shouldExecuteCommand() {
		// given
		Command command = mock(Command.class);

		// when
		testObj.setCommand(command);
		testObj.run();

		// then
		verify(command).execute();
	}
}
