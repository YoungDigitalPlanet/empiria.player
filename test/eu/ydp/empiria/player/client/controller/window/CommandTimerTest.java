package eu.ydp.empiria.player.client.controller.window;

import static org.mockito.Mockito.*;

import com.google.gwt.user.client.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandTimerTest {

	@Mock
	private Command command;

	private final CommandTimer testObj = new CommandTimer();

	@Test
	public void shouldExecuteCommand() {
		// given
		testObj.setCommand(command);

		// when
		testObj.run();

		// then
		verify(command).execute();
	}
}
