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
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.module.media.MimeSourceProvider;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;

@RunWith(MockitoJUnitRunner.class)
public class SoundJsPluginTest {

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

	@Captor
	private ArgumentCaptor<CallbackRecevier<MediaWrapper<Widget>>> cbCaptor;

	private final String fileSrc = "file.mp3";

	@Before
	public void setUp() {
		Map<String, String> sourcesWithTypes = getSourcesWithTypes();
		when(mimeSourceProvider.getSourcesWithTypeByExtension(fileSrc)).thenReturn(sourcesWithTypes);
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

	private void verifyMediaWrapperCreation(Map<String, String> sourcesWithTypes) {
		verify(mimeSourceProvider).getSourcesWithTypeByExtension(fileSrc);
		verify(mediaWrapperCreator).createMediaWrapper(eq(fileSrc), eq(sourcesWithTypes), cbCaptor.capture());
	}

	private Map<String, String> getSourcesWithTypes() {
		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		sourcesWithTypes.put(fileSrc, "audio/mp4");

		return sourcesWithTypes;
	}
}
