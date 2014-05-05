package eu.ydp.empiria.player.client.media;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.MimeUtil;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class SoundPlayerTest {
	private final String FILE = "test.mp3";
	private final String AUDIO_MP4_MIME = "audio/mp4";

	@InjectMocks
	private SoundPlayer testObj;

	@Mock
	private EventsBus eventsBus;

	@Mock
	private MimeUtil mimeUtil;

	@Mock
	private MediaWrapperCreator mediaWrapperCreator;

	@Mock
	private HideNativeMediaControlsManager nativeMediaControlsManager;

	@Captor
	private ArgumentCaptor<MediaEvent> argumentCaptor;

	private final MocksCollector mocksCollector = new MocksCollector();

	@Test
	public void shouldPlayFirstFile() {
		// given
		doReturn(AUDIO_MP4_MIME).when(mimeUtil).getMimeTypeFromExtension(FILE);

		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		sourcesWithTypes.put(FILE, AUDIO_MP4_MIME);

		// when
		testObj.play(FILE);

		// then
		verify(mediaWrapperCreator).createMediaWrapper(FILE, sourcesWithTypes, testObj);
	}

	@Test
	public void shouldNotPlayWhenFilePathIsNull() {
		// given
		String filePath = null;

		// when
		testObj.play(filePath);

		// then
		verifyZeroInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldPlaySameFileTwice() {
		// given
		doReturn(AUDIO_MP4_MIME).when(mimeUtil).getMimeTypeFromExtension(FILE);

		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		sourcesWithTypes.put(FILE, AUDIO_MP4_MIME);

		testObj.play(FILE);

		// when
		testObj.play(FILE);

		// then
		verify(mediaWrapperCreator).createMediaWrapper(FILE, sourcesWithTypes, testObj);
	}

	@Test
	public void shouldStopWhilePlaying() {
		// given
		MediaWrapper<Widget> mediaWrapper = mock(MediaWrapper.class);

		// when
		testObj.setCallbackReturnObject(mediaWrapper);
		testObj.stop();

		// then
		verify(nativeMediaControlsManager).addToDocumentAndHideControls(mediaWrapper);
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), mediaWrapper, testObj);
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), mediaWrapper, testObj);
		verify(eventsBus, times(2)).fireEventFromSource(argumentCaptor.capture(), eq(mediaWrapper));
		verifyNoMoreInteractions(mocksCollector.getMocks());

		List<MediaEvent> capturedEvents = argumentCaptor.getAllValues();

		assertEquals(MediaEventTypes.PLAY, capturedEvents.get(0).getType());
		assertEquals(MediaEventTypes.STOP, capturedEvents.get(1).getType());
	}

	@Test
	public void shouldNotStopWhenNotPlaying() {
		// when
		testObj.stop();

		verifyZeroInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldSetValuesAndStartPlayingOnCallback() {
		// given
		MediaWrapper<Widget> mediaWrapper = mock(MediaWrapper.class);

		// when
		testObj.setCallbackReturnObject(mediaWrapper);

		// then
		verify(nativeMediaControlsManager).addToDocumentAndHideControls(mediaWrapper);
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), mediaWrapper, testObj);
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), mediaWrapper, testObj);
		verify(eventsBus).fireEventFromSource(argumentCaptor.capture(), eq(mediaWrapper));
		verifyNoMoreInteractions(mocksCollector.getMocks());

		assertEquals(argumentCaptor.getValue().getType(), MediaEventTypes.PLAY);
	}

	@Test
	public void shouldNotProcessEventForDifferentWrapper() {
		// given
		MediaWrapper<Widget> mediaWrapper = mock(MediaWrapper.class);
		MediaWrapper<Widget> eventMediaWrapper = mock(MediaWrapper.class);
		MediaEvent mediaEvent = mock(MediaEvent.class);

		doReturn(eventMediaWrapper).when(mediaEvent).getMediaWrapper();

		testObj.setCallbackReturnObject(mediaWrapper);

		// when
		testObj.onMediaEvent(mediaEvent);

		// then
		verify(nativeMediaControlsManager).addToDocumentAndHideControls(mediaWrapper);
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), mediaWrapper, testObj);
		verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), mediaWrapper, testObj);
		verify(eventsBus).fireEventFromSource(any(MediaEvent.class), eq(mediaWrapper));
		verify(mediaEvent).getMediaWrapper();
		verifyNoMoreInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldNotPropagateOnEndEventWhenNoExternalHandlerSet() {
		// given
		MediaWrapper<Widget> mediaWrapper = mock(MediaWrapper.class);
		MediaEvent mediaEvent = mock(MediaEvent.class);

		doReturn(MediaEventTypes.ON_END).when(mediaEvent).getType();
		doReturn(mediaWrapper).when(mediaEvent).getMediaWrapper();

		// when
		testObj.setCallbackReturnObject(mediaWrapper);
		testObj.onMediaEvent(mediaEvent);

		// then
		verify(mediaEvent).getMediaWrapper();
		verify(mediaEvent).getType();
	}

	@Test
	public void shouldPropagateOnEndEventWhenHandlerSet() {
		// given
		MediaWrapper<Widget> mediaWrapper = mock(MediaWrapper.class);
		MediaEvent mediaEvent = mock(MediaEvent.class);
		MediaEventHandler mediaEventExternalHandler = mock(MediaEventHandler.class);

		doReturn(MediaEventTypes.ON_END).when(mediaEvent).getType();
		doReturn(mediaWrapper).when(mediaEvent).getMediaWrapper();

		// when
		testObj.setCallbackReturnObject(mediaWrapper);
		testObj.addExternalHandler(mediaEventExternalHandler);
		testObj.onMediaEvent(mediaEvent);

		// then
		verify(mediaEvent).getMediaWrapper();
		verify(mediaEvent).getType();
	}

	@Test
	public void shouldCatchOnPlayEvent() {
		// given
		MediaWrapper<Widget> mediaWrapper = mock(MediaWrapper.class);
		MediaEvent mediaEvent = mock(MediaEvent.class);

		doReturn(MediaEventTypes.ON_PLAY).when(mediaEvent).getType();
		doReturn(mediaWrapper).when(mediaEvent).getMediaWrapper();

		// when
		testObj.setCallbackReturnObject(mediaWrapper);
		testObj.onMediaEvent(mediaEvent);

		verify(mediaEvent).getMediaWrapper();
		verify(mediaEvent).getType();
	}

	@Test
	public void shouldAddExternalHandler() {
		// given
		MediaEventHandler handler = mock(MediaEventHandler.class);

		// when
		testObj.addExternalHandler(handler);

		// then
		verifyNoMoreInteractions(mocksCollector.getMocks());
	}
}
