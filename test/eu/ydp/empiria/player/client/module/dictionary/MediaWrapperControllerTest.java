package eu.ydp.empiria.player.client.module.dictionary;

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

import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.dictionary.external.MediaWrapperController;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@RunWith(MockitoJUnitRunner.class)
public class MediaWrapperControllerTest {

	@InjectMocks
	private MediaWrapperController testObj;

	@Mock
	private EventsBus eventsBus;

	@Mock
	private HideNativeMediaControlsManager hideNativeMediaControlsManager;

	@Captor
	private ArgumentCaptor<MediaEvent> mediaEventCaptor;

	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Test
	public void shouldAddMediaWrapperControls() {
		// when
		testObj.addMediaWrapperControls(mediaWrapper);

		// then
		verify(hideNativeMediaControlsManager).addToDocumentAndHideControls(mediaWrapper);
	}

	@Test
	public void shouldPlay() {
		// when
		testObj.play(mediaWrapper);

		// then
		verify(eventsBus, times(2)).fireEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));

		List<MediaEvent> calledMediaEvents = mediaEventCaptor.getAllValues();
		MediaEvent calledMediaEvent1 = calledMediaEvents.get(0);
		MediaEvent calledMediaEvent2 = calledMediaEvents.get(1);

		assertMediaEvent(calledMediaEvent1, MediaEventTypes.STOP, mediaWrapper);
		assertMediaEvent(calledMediaEvent2, MediaEventTypes.PLAY, mediaWrapper);
	}

	@Test
	public void shouldStop() {
		// when
		testObj.stop(mediaWrapper);

		// then
		verify(eventsBus).fireEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));

		MediaEvent event = mediaEventCaptor.getValue();
		assertMediaEvent(event, MediaEventTypes.PAUSE, mediaWrapper);
	}

	private void assertMediaEvent(MediaEvent mediaEvent, MediaEventTypes assumedType, MediaWrapper<Widget> assumedMediaWrapper) {
		assertEquals(assumedType, mediaEvent.getType());
		assertEquals(assumedMediaWrapper, mediaEvent.getMediaWrapper());
	}
}
