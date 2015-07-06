package eu.ydp.empiria.player.client.module.media;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

@RunWith(JUnitParamsRunner.class)
public class MediaWrapperControllerJUnitTest {

	@InjectMocks
	private MediaWrapperController testObj;

	@Mock
	private EventsBus eventsBus;

	@Captor
	private ArgumentCaptor<MediaEvent> mediaEventCaptor;

	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldPlay() {
		// when
		testObj.play(mediaWrapper);

		// then
		verifyMediaEvent(MediaEventTypes.PLAY, mediaWrapper);
	}

	@Test
	public void shouldPlayLooped() {
		// when
		testObj.playLooped(mediaWrapper);

		// then
		verifyMediaEvent(MediaEventTypes.PLAY_LOOPED, mediaWrapper);
	}

	@Test
	public void shouldStop() {
		// when
		testObj.stop(mediaWrapper);

		// then
		verifyMediaEvent(MediaEventTypes.STOP, mediaWrapper);
	}

	@Test
	public void shouldPause() {
		// when
		testObj.pause(mediaWrapper);

		// then
		verifyMediaEvent(MediaEventTypes.PAUSE, mediaWrapper);
	}

	@Test
	public void shouldResume() {
		// when
		testObj.resume(mediaWrapper);

		// then
		verifyMediaEvent(MediaEventTypes.RESUME, mediaWrapper);
	}

	@Test
	public void shouldStopAndPlay() {
		// when
		testObj.stopAndPlay(mediaWrapper);

		// then
		verify(eventsBus, times(2)).fireEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));

		List<MediaEvent> calledMediaEvents = mediaEventCaptor.getAllValues();
		MediaEvent calledStopEvent = calledMediaEvents.get(0);
		MediaEvent calledPlayEvent = calledMediaEvents.get(1);

		assertMediaEvent(calledStopEvent, MediaEventTypes.STOP, mediaWrapper);
		assertMediaEvent(calledPlayEvent, MediaEventTypes.PLAY, mediaWrapper);
	}

	@Test
	public void shouldPauseAndPlay() {
		// when
		testObj.pauseAndPlay(mediaWrapper);

		// then
		verify(eventsBus, times(2)).fireEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));

		List<MediaEvent> calledMediaEvents = mediaEventCaptor.getAllValues();
		MediaEvent calledStopEvent = calledMediaEvents.get(0);
		MediaEvent calledPlayEvent = calledMediaEvents.get(1);

		assertMediaEvent(calledStopEvent, MediaEventTypes.PAUSE, mediaWrapper);
		assertMediaEvent(calledPlayEvent, MediaEventTypes.PLAY, mediaWrapper);
	}

	@Test
	public void shouldPauseAndPlayLooped() {
		// when
		testObj.pauseAndPlayLooped(mediaWrapper);

		// then
		verify(eventsBus, times(2)).fireEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));

		List<MediaEvent> calledMediaEvents = mediaEventCaptor.getAllValues();
		MediaEvent calledStopEvent = calledMediaEvents.get(0);
		MediaEvent calledPlayEvent = calledMediaEvents.get(1);

		assertMediaEvent(calledStopEvent, MediaEventTypes.PAUSE, mediaWrapper);
		assertMediaEvent(calledPlayEvent, MediaEventTypes.PLAY_LOOPED, mediaWrapper);
	}

	@Test
	@Parameters
	public void shouldAddHandler(MediaEventTypes type) {
		// given
		MediaEventHandler handler = mock(MediaEventHandler.class);

		// when
		testObj.addHandler(type, mediaWrapper, handler);

		// then
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(type), mediaWrapper, handler);
	}

	public Object[] parametersForShouldAddHandler() {
		return MediaEventTypes.values();
	}

	@Test
	public void shouldSetCurrentTime() {
		// given
		Double time = 1.23;

		// when
		testObj.setCurrentTime(mediaWrapper, time);

		// then
		verify(eventsBus).fireEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));
		assertEquals(mediaEventCaptor.getValue().getType(), MediaEventTypes.SET_CURRENT_TIME);
		assertEquals(mediaEventCaptor.getValue().getCurrentTime(), time);
	}

	@Test
	public void shouldGetCurrentTime() {
		// given
		Double time = 1.23;
		when(mediaWrapper.getCurrentTime()).thenReturn(time);

		// when
		double result = testObj.getCurrentTime(mediaWrapper);

		assertEquals(time, result, 0);
	}

	private void verifyMediaEvent(MediaEventTypes assumedEventType, MediaWrapper<Widget> assumeMediaWrapper) {
		verify(eventsBus).fireEventFromSource(mediaEventCaptor.capture(), eq(assumeMediaWrapper));

		assertMediaEvent(mediaEventCaptor.getValue(), assumedEventType, assumeMediaWrapper);
	}

	private void assertMediaEvent(MediaEvent mediaEvent, MediaEventTypes assumedType, MediaWrapper<Widget> assumedMediaWrapper) {
		assertEquals(assumedType, mediaEvent.getType());
		assertEquals(assumedMediaWrapper, mediaEvent.getMediaWrapper());
	}
}
