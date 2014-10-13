package eu.ydp.empiria.player.client.module.simulation.soundjs;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.module.media.MimeSourceProvider;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@RunWith(GwtMockitoTestRunner.class)
public class SoundJsPluginJUnitTest {

	@InjectMocks
	private SoundJsPlugin testObj;

	@Mock
	private MediaWrapperCreator mediaWrapperCreator;
	@Mock
	private MimeSourceProvider mimeSourceProvider;
	@Mock
	private MediaWrapperController mediaWrapperController;
	@Mock
	private MediaWrapper<Widget> mediaWrapper;
	@Mock
	private SoundJsNative soundJsNative;
	@Mock
	private EventsBus eventsBus;

	@Mock
	private MediaEvent mediaEvent;
	@Mock
	private MediaEventHandler mediaEventHandler;

	@Captor
	private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> cbCaptor;
	@Captor
	private ArgumentCaptor<MediaEventHandler> mediaEventCaptor;

	private final String fileSrc = "file.mp3";

	@Before
	public void setUp() {
		Map<String, String> sourcesWithTypes = getSourcesWithTypes();
		when(mimeSourceProvider.getSourcesWithTypeByExtension(fileSrc)).thenReturn(sourcesWithTypes);

		AbstractHTML5MediaWrapper mediaEventWrapper = mock(AbstractHTML5MediaWrapper.class, RETURNS_DEEP_STUBS);
		when(mediaEvent.getMediaWrapper()).thenReturn((MediaWrapper) mediaEventWrapper);
		when(mediaEventWrapper.getMediaBase().getCurrentSrc()).thenReturn(fileSrc);
	}

	@Test
	public void shouldSetApiForJs() {
		verify(soundJsNative).setApiForJs(testObj);
	}

	@Test
	public void shouldPreloadFile() {
		// given
		Map<String, String> sourcesWithTypes = getSourcesWithTypes();
		when(mimeSourceProvider.getSourcesWithTypeByExtension(fileSrc)).thenReturn(sourcesWithTypes);

		// when
		testObj.preload(fileSrc);

		// then
		verifyMediaWrapperCreation(sourcesWithTypes);
	}

	@Test
	public void shouldNotPreloadFile() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.preload(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.preload(fileSrc);

		// then
		verifyNoMoreInteractions(mediaWrapperCreator);
	}

	@Test
	public void shouldCreateWrapperAndPlay() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();

		// when
		testObj.play(fileSrc);

		// then
		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);
		verify(mediaWrapperController).stopAndPlay(mediaWrapper);
	}

	@Test
	public void shouldPlayAlreadyPreloaded() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.preload(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.play(fileSrc);

		// then
		verifyNoMoreInteractions(mediaWrapperCreator);
		verify(mediaWrapperController).stopAndPlay(mediaWrapper);
	}

	@Test
	public void shouldCallNativeOnComplete_ifPreloadedAndPlayed() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_END);
		testObj.preload(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// then
		testObj.play(fileSrc);

		// then
		verify(mediaWrapperController).addHandler(eq(MediaEventTypes.ON_END), eq(mediaWrapper), mediaEventCaptor.capture());
		verifyNoMoreInteractions(eventsBus);
		mediaEventCaptor.getValue().onMediaEvent(mediaEvent);
		verify(soundJsNative).onComplete(fileSrc);
	}

	@Test
	public void shouldCallNativeOnComplete_ifPlayingSecondTime() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_END);
		testObj.play(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// then
		testObj.play(fileSrc);

		// then
		verify(mediaWrapperController).addHandler(eq(MediaEventTypes.ON_END), eq(mediaWrapper), mediaEventCaptor.capture());
		verifyNoMoreInteractions(eventsBus);
		mediaEventCaptor.getValue().onMediaEvent(mediaEvent);
		verify(soundJsNative).onComplete(fileSrc);
	}

	@Test
	public void shouldCallNativeOnComplete_ifPlayed() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_END);

		// when
		testObj.play(fileSrc);

		// then
		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verify(mediaWrapperController).addHandler(eq(MediaEventTypes.ON_END), eq(mediaWrapper), mediaEventCaptor.capture());
		mediaEventCaptor.getValue().onMediaEvent(mediaEvent);
		verify(soundJsNative).onComplete(fileSrc);
	}

	@Test
	public void shouldNotCallNativeOnComplete_ifNotOnEndEvent() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.PAUSE);

		// when
		testObj.play(fileSrc);

		// then
		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verify(mediaWrapperController).addHandler(eq(MediaEventTypes.ON_END), eq(mediaWrapper), mediaEventCaptor.capture());
		mediaEventCaptor.getValue().onMediaEvent(mediaEvent);
		verify(soundJsNative, never()).onComplete(fileSrc);
	}

	@Test
	public void shouldPlayAlreadyPlayed() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.play(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.play(fileSrc);

		// then
		verifyNoMoreInteractions(mediaWrapperCreator);
		verify(mediaWrapperController, times(2)).stopAndPlay(mediaWrapper);
	}

	@Test
	public void shouldCreateWrapperAndPlayLooped() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();

		// when
		testObj.playLooped(fileSrc);

		// then
		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);
		verify(mediaWrapperController).stopAndPlayLooped(mediaWrapper);
	}

	@Test
	public void shouldPlayLoopedAlreadyPreloaded() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.preload(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.playLooped(fileSrc);

		// then
		verifyNoMoreInteractions(mediaWrapperCreator);
		verify(mediaWrapperController).stopAndPlayLooped(mediaWrapper);
	}

	@Test
	public void shouldCallNativeOnComplete_ifPlayingLoopedSecondTime() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_END);
		testObj.playLooped(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// then
		testObj.playLooped(fileSrc);

		// then
		verify(mediaWrapperController).addHandler(eq(MediaEventTypes.ON_END), eq(mediaWrapper), mediaEventCaptor.capture());
		verifyNoMoreInteractions(eventsBus);
		mediaEventCaptor.getValue().onMediaEvent(mediaEvent);
		verify(soundJsNative).onComplete(fileSrc);
	}

	@Test
	public void shouldPlayLoppedAlreadyPlayedLooped() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.playLooped(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.playLooped(fileSrc);

		// then
		verifyNoMoreInteractions(mediaWrapperCreator);
		verify(mediaWrapperController, times(2)).stopAndPlayLooped(mediaWrapper);
	}

	@Test
	public void shouldStop() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.play(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.stop(fileSrc);

		// then
		verify(mediaWrapperController).stop(mediaWrapper);
	}

	@Test
	public void shouldPause() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.play(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.pause(fileSrc);

		// then
		verify(mediaWrapperController).pause(mediaWrapper);
	}

	@Test
	public void shouldResume() {
		// given
		Map<String, String> assumedSourcesWithTypes = getSourcesWithTypes();
		testObj.play(fileSrc);

		verifyMediaWrapperCreation(assumedSourcesWithTypes);
		cbCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		// when
		testObj.resume(fileSrc);

		// then
		verify(mediaWrapperController).resume(mediaWrapper);
	}

	private void verifyMediaWrapperCreation(Map<String, String> sourcesWithTypes) {
		verify(mimeSourceProvider).getSourcesWithTypeByExtension(fileSrc);
		verify(mediaWrapperCreator).createSimulationMediaWrapper(eq(fileSrc), eq(sourcesWithTypes), cbCaptor.capture());
	}

	private Map<String, String> getSourcesWithTypes() {
		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		sourcesWithTypes.put(fileSrc, "audio/mp4");

		return sourcesWithTypes;
	}
}
