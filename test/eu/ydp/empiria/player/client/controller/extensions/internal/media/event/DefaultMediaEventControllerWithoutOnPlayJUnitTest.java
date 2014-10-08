package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.*;
import static junitparams.JUnitParamsRunner.*;
import static org.mockito.Mockito.*;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;

@RunWith(JUnitParamsRunner.class)
public class DefaultMediaEventControllerWithoutOnPlayJUnitTest {

	@InjectMocks
	private DefaultMediaEventControllerWithoutOnPlay testObj;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	protected MediaEvent mediaEvent;
	@Mock
	private Logger logger;

	@Mock
	protected AbstractMediaProcessor mediaProcessor;
	@Mock
	protected MediaExecutor<MediaBase> mediaExecutor;
	@Mock
	protected MediaWrapper mediaWrapper;

	private static List<MediaEventTypes> mappedEvents = Lists.newArrayList(CHANGE_VOLUME, STOP, PAUSE, SET_CURRENT_TIME, PLAY, MUTE, ENDED, ON_END, ON_ERROR);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(mediaExecutor.getMediaWrapper()).thenReturn(mediaWrapper);
	}

	@Test
	public void shouldProcess_onChangeVolume() {
		// given
		double volume = 10.25;
		double assumedVolume = 10.25;
		when(mediaEvent.getAssociatedType().getType()).thenReturn(CHANGE_VOLUME);
		when(mediaEvent.getVolume()).thenReturn(volume);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).setVolume(assumedVolume);
	}

	@Test
	public void shouldProcess_stop() {
		// given
		when(mediaEvent.getAssociatedType().getType()).thenReturn(STOP);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).stop();
	}

	@Test
	public void shouldProcess_pause() {
		// given
		when(mediaEvent.getAssociatedType().getType()).thenReturn(PAUSE);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).pause();
	}

	@Test
	public void shouldProcess_setCurrentTime() {
		// given
		double currentTime = 10.25;
		double assumedCurrentTime = 10.25;
		when(mediaEvent.getAssociatedType().getType()).thenReturn(SET_CURRENT_TIME);
		when(mediaEvent.getCurrentTime()).thenReturn(currentTime);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).setCurrentTime(assumedCurrentTime);
	}

	@Test
	public void shouldProcess_play() {
		// given
		when(mediaEvent.getAssociatedType().getType()).thenReturn(PLAY);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).play();
	}

	@Test
	public void shouldProcess_mute() {
		// given
		boolean isMuted = false;
		boolean assumedIsMuted = true;
		when(mediaEvent.getAssociatedType().getType()).thenReturn(MUTE);
		when(mediaExecutor.getMediaWrapper().isMuted()).thenReturn(isMuted);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).setMuted(assumedIsMuted);
	}

	@Test
	public void shouldProcess_unMute() {
		// given
		boolean isMuted = true;
		boolean assumedIsMuted = false;
		when(mediaEvent.getAssociatedType().getType()).thenReturn(MUTE);
		when(mediaExecutor.getMediaWrapper().isMuted()).thenReturn(isMuted);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).setMuted(assumedIsMuted);
	}

	@Test
	public void shouldProcess_ended() {
		// given
		when(mediaEvent.getAssociatedType().getType()).thenReturn(ENDED);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).stop();
	}

	@Test
	public void shouldProcess_onEnd() {
		// given
		when(mediaEvent.getAssociatedType().getType()).thenReturn(ON_END);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(mediaExecutor).stop();
	}

	@Test
	public void shouldProcess_onError() {
		// given
		String assumedErrorMsg = "Media Error";
		when(mediaEvent.getAssociatedType().getType()).thenReturn(ON_ERROR);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verify(logger).info(assumedErrorMsg);
	}

	@Test
	@Parameters
	public void shouldNotProcessOtherEvents(MediaEventTypes type) {
		// given
		when(mediaEvent.getAssociatedType().getType()).thenReturn(type);

		// when
		testObj.onMediaEvent(mediaEvent, mediaExecutor, mediaProcessor);

		// then
		verifyZeroInteractions(mediaExecutor, mediaProcessor, logger);
	}

	public Object[] parametersForShouldNotProcessOtherEvents() {
		List<MediaEventTypes> events = FluentIterable.from(Lists.newArrayList(MediaEventTypes.values())).filter(new Predicate<MediaEventTypes>() {

			@Override
			public boolean apply(MediaEventTypes type) {
				return !mappedEvents.contains(type);
			}

		}).toList();

		return $(events.toArray());
	}

}
