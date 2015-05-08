package eu.ydp.empiria.player.client.module.external.sound;

import eu.ydp.empiria.player.client.module.external.ExternalInteractionPaths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionSoundApiTest {

	@InjectMocks
	private ExternalInteractionSoundApi testObj;
	@Mock
	private MediaWrapperSoundActions soundActions;
	@Mock
	private ExternalInteractionSoundWrappers wrappers;
	@Mock
	private ExternalInteractionPaths paths;
	@Mock
	private MediaWrapperAction action;

	private String src = "src";
	private String filePath = "filePath";

	@Before
	public void init() {
		when(paths.getExternalFilePath(src)).thenReturn(filePath);
	}

	@Test
	public void shouldExecutePlayAction() {
		// given
		when(soundActions.getPlayAction()).thenReturn(action);

		// when
		testObj.play(src);

		// then
		verify(wrappers).execute(filePath, action);
	}

	@Test
	public void shouldExecutePlayLoopedAction() {
		// given
		when(soundActions.getPlayLoopedAction()).thenReturn(action);

		// when
		testObj.playLooped(src);

		// then
		verify(wrappers).execute(filePath, action);
	}

	@Test
	public void shouldExecuteStopAction() {
		// given
		when(soundActions.getStopAction()).thenReturn(action);

		// when
		testObj.stop(src);

		// then
		verify(wrappers).execute(filePath, action);
	}

	@Test
	public void shouldExecutePauseAction() {
		// given
		when(soundActions.getPauseAction()).thenReturn(action);

		// when
		testObj.pause(src);

		// then
		verify(wrappers).execute(filePath, action);
	}

	@Test
	public void shouldExecuteResumeAction() {
		// given
		when(soundActions.getResumeAction()).thenReturn(action);

		// when
		testObj.resume(src);

		// then
		verify(wrappers).execute(filePath, action);
	}
}