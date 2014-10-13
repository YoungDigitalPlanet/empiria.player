package eu.ydp.empiria.player.client.module.media;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@RunWith(MockitoJUnitRunner.class)
public class MediaWrapperControllerJUnitTest {

	@InjectMocks
	private MediaWrapperController testObj;

	@Mock
	private EventsBus eventsBus;

	@Captor
	private ArgumentCaptor<MediaEvent> mediaEventCaptor;

	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Test
	public void shouldPlay() {
		// when
		testObj.play(mediaWrapper);

		// then
		verifyMediaEvent(MediaEventTypes.PLAY, mediaWrapper);
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
	public void shouldAddHandler() {
		// given
		MediaEventTypes type = MediaEventTypes.ON_END;
		MediaEventHandler handler = mock(MediaEventHandler.class);

		// when
		testObj.addHandler(type, mediaWrapper, handler);

		// then
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(type), mediaWrapper, handler);
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
