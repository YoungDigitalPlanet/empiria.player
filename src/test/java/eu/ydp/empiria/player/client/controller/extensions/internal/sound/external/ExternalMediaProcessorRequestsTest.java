package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@RunWith(JUnitParamsRunner.class)
public class ExternalMediaProcessorRequestsTest extends ExternalMediaProcessorTestBase {

	@SuppressWarnings("unused")
	private Object[] media_params() {
		return $((Object[]) TestMediaAudio.values());
	}

	@Test
	@Parameters(method = "media_params")
	public void init(TestMediaAudio testMedias) {
		// when
		container.createMediaWrapper(testMedias);

		// then
		verify(connector).init(anyString(), eq(testMedias.getSources()));
	}

	@Test
	@Parameters(method = "media_params")
	public void play(TestMediaAudio testMedias) {
		// given
		MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);

		// when
		container.fireMediaEvent(MediaEventTypes.PLAY, wrapper);

		// then
		verify(connector).play(eq(wrapper.getMediaUniqId()));
	}

	@Test
	@Parameters(method = "media_params")
	public void pause(TestMediaAudio testMedias) {
		// given
		MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);

		// when
		container.fireMediaEvent(MediaEventTypes.PAUSE, wrapper);

		// then
		verify(connector).pause(eq(wrapper.getMediaUniqId()));
	}

	@Test
	@Parameters(method = "media_params")
	public void stop(TestMediaAudio testMedias) {
		// given
		MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);

		// when
		container.fireMediaEvent(MediaEventTypes.STOP, wrapper);

		// then
		verify(connector).pause(eq(wrapper.getMediaUniqId()));
		verify(connector).seek(eq(wrapper.getMediaUniqId()), eq(ExternalMediaEngine.PLAY_INITIAL_TIME));
	}

	@Test
	@Parameters(method = "media_params")
	public void seek(TestMediaAudio testMedias) {
		// given
		final Integer NEW_POSITION = 919;
		final int MILLIS_FACTOR = 1000;

		MediaWrapper<Widget> wrapper = container.createMediaWrapper(testMedias);
		MediaEvent mediaEvent = new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, wrapper);
		mediaEvent.setCurrentTime(NEW_POSITION.doubleValue());

		// when
		container.fireMediaEvent(mediaEvent);

		// then
		verify(connector).seek(eq(wrapper.getMediaUniqId()), eq(NEW_POSITION * MILLIS_FACTOR));
	}

}
