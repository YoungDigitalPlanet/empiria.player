package eu.ydp.empiria.player.client.module.external.common.sound;

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
public class ExternalSoundInstanceTest {

	@InjectMocks
	private ExternalSoundInstance testObj;
	@Mock
	private MediaWrapperController mediaWrapperController;
	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Test
	public void shouldDelegatePlay() {
		// when
		testObj.play();

		// then
		verify(mediaWrapperController).stopAndPlay(mediaWrapper);
	}

	@Test
	public void shouldDelegatePlayLooped() {
		// when
		testObj.playLooped();

		// then
		verify(mediaWrapperController).playLooped(mediaWrapper);
	}

	@Test
	public void shouldDelegateStop() {
		// when
		testObj.stop();

		// then
		verify(mediaWrapperController).stop(mediaWrapper);
	}

	@Test
	public void shouldDelegatePause() {
		// when
		testObj.pause();

		// then
		verify(mediaWrapperController).pause(mediaWrapper);
	}

	@Test
	public void shouldDelegateResume() {
		// when
		testObj.resume();

		// then
		verify(mediaWrapperController).resume(mediaWrapper);
	}
}
