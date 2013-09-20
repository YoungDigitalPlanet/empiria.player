package eu.ydp.empiria.player.client.module.tutor.commands;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.tutor.ShowImageDTO;
import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ShowImageCommandTest {

	ShowImageCommand command;
	@Mock
	TutorView view;
	@Mock
	TutorEndHandler endHandler;

	final static String ASSET_PATH = "ALEX_JUMP";
	private final Size size = new Size(43, 15);

	@Before
	public void setUp() {
		ShowImageDTO showImageDTO = new ShowImageDTO(ASSET_PATH, size);
		command = new ShowImageCommand(view, showImageDTO, endHandler);
	}

	@Test
	public void shouldExecuteCommand() {
		// when
		command.execute();

		// then
		verify(view).setBackgroundImage(ASSET_PATH, size);
		assertThat(command.isFinished(), is(true));
		verify(endHandler).onEnd(false);
	}

	@Test
	public void shouldTerminateCommand() {
		// when
		command.terminate();

		// then
		assertThat(command.isFinished(), is(true));
		verify(endHandler, never()).onEnd(anyBoolean());
	}
}
