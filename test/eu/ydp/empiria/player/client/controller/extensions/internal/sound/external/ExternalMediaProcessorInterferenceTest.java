package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

@SuppressWarnings("unused")
@RunWith(JUnitParamsRunner.class)
public class ExternalMediaProcessorInterferenceTest extends ExternalMediaProcessorTestBase {

	private Object[] audio_params() {
		List<List<TestMediaAudio>> lists = Lists.newArrayList();
		for (TestMediaAudio audio0 : TestMediaAudio.values()) {
			for (TestMediaAudio audio1 : TestMediaAudio.values()) {
				lists.add(Lists.newArrayList(audio0, audio1));
			}
		}
		return $(lists.toArray());
	}

	@Test
	@Parameters(method = "audio_params")
	public void stopOtherOnPlay(List<TestMediaAudio> testMedias) {
		// given
		List<MediaWrapper<Widget>> wrappers = container.createMediaWrappers(testMedias);
		container.forwardPlayFromConnectorToListener();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), wrappers.get(0), handler);

		container.fireMediaEvent(MediaEventTypes.PLAY, wrappers.get(0));

		// when
		container.fireMediaEvent(MediaEventTypes.PLAY, wrappers.get(1));

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_PAUSE));
	}

	@Test
	@Parameters(method = "audio_params")
	public void stopOtherOnPageSwitch(List<TestMediaAudio> testMedias) {
		// given
		List<MediaWrapper<Widget>> wrappers = container.createMediaWrappers(testMedias);
		container.forwardPlayFromConnectorToListener();
		container.forwardPauseFromConnectorToListener();
		MediaEventHandler handler0 = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), wrappers.get(0), handler0);
		MediaEventHandler handler1 = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), wrappers.get(1), handler1);

		listener.onPlay(wrappers.get(0).getMediaUniqId());

		// when
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_UNLOADED));

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler0).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_PAUSE));

		verify(handler1, never()).onMediaEvent(any(MediaEvent.class));
	}

	private Object[] parametersForOnPlay_multiple() {
		List<MediaEventTypes> mets = Lists.newArrayList();
		mets.addAll(Arrays.asList(MediaEventTypes.values()));
		mets.remove(MediaEventTypes.PLAY);
		return $(mets.toArray());
	}

	@Test
	@Parameters
	public void onPlay_multiple(MediaEventTypes type) {
		// given
		container.forwardPlayFromConnectorToListener();
		container.forwardPauseFromConnectorToListener();
		MediaWrapper<Widget> wrapper0 = container.createMediaWrapper(TestMediaAudio.SINGLE_MP3_0);
		MediaWrapper<Widget> wrapper1 = container.createMediaWrapper(TestMediaAudio.SINGLE_MP3_1);
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), wrapper0, handler);

		container.fireMediaEvent(MediaEventTypes.PLAY, wrapper0);
		container.fireMediaEvent(type, wrapper1);

		// when
		container.firePlayerEvent(PlayerEventTypes.PAGE_UNLOADED);

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler, times(1)).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_PAUSE));
	}

}
