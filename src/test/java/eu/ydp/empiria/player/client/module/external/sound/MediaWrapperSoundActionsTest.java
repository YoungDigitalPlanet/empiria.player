package eu.ydp.empiria.player.client.module.external.sound;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MediaWrapperSoundActionsTest {

	@InjectMocks
	private MediaWrapperSoundActions testObj;
	@Mock
	private MediaWrapperController mediaWrapperController;
	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Test
	public void shouldExecuteStopAndPlay() {
		// given
		MediaWrapperAction playAction = testObj.getPlayAction();

		// when
		playAction.execute(mediaWrapper);

		// then
		verify(mediaWrapperController).stopAndPlay(mediaWrapper);
	}

	@Test
	public void shouldExecutePlayLooped() {
		// given
		MediaWrapperAction playAction = testObj.getPlayLoopedAction();

		// when
		playAction.execute(mediaWrapper);

		// then
		verify(mediaWrapperController).playLooped(mediaWrapper);
	}

	@Test
	public void shouldExecuteStop() {
		// given
		MediaWrapperAction playAction = testObj.getStopAction();

		// when
		playAction.execute(mediaWrapper);

		// then
		verify(mediaWrapperController).stop(mediaWrapper);
	}

	@Test
	public void shouldExecutePause() {
		// given
		MediaWrapperAction playAction = testObj.getPauseAction();

		// when
		playAction.execute(mediaWrapper);

		// then
		verify(mediaWrapperController).pause(mediaWrapper);
	}

	@Test
	public void shouldExecuteResume() {
		// given
		MediaWrapperAction playAction = testObj.getResumeAction();

		// when
		playAction.execute(mediaWrapper);

		// then
		verify(mediaWrapperController).resume(mediaWrapper);
	}
}