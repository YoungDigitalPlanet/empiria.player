package eu.ydp.empiria.player.client.module.tutor.commands;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.tutor.EndHandler;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;

@RunWith(MockitoJUnitRunner.class)
public class ShowImageCommandTest {

	ShowImageCommand command;
	@Mock
	TutorView view;

	final static String ASSET_PATH = "ALEX_JUMP";
	@Mock
	EndHandler endHandler;

	@Before
	public void setUp() {
		command = new ShowImageCommand(view, ASSET_PATH, endHandler);
	}

	@Test
	public void shouldExecuteCommand() {
		// when
		command.execute();

		// then
		verify(view).setAnimationImage(ASSET_PATH);
		verify(endHandler).onEnd();
		assertThat(command.isFinished(), is(true));
	}

	@Test
	public void shouldTerminateCommand() {
		// when
		command.terminate();

		// then
		verify(endHandler, never()).onEnd();
		assertThat(command.isFinished(), is(true));
	}
}
