package eu.ydp.empiria.player.client.module.slideshow.sound;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.*;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlideSoundsTest {

	@InjectMocks
	private SlideSounds testObj;
	@Mock
	private MediaWrapperCreator mediaWrapperCreator;
	@Mock
	private MimeSourceProvider mimeSourceProvider;
	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Captor
	private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackReceiverCaptor;

	private final Map<String, String> sourceWithType = Maps.newHashMap();

	@Test
	public void shouldAddSoundToList_whenSoundIsNotOnList() {
		// given
		String filepath = "test.mp3";
		String filepath2 = "test2.mp3";

		// when
		addSound(filepath);
		addSound(filepath2);

		MediaWrapper<Widget> result = testObj.getSound(filepath);

		// than
		assertThat(result).isEqualTo(mediaWrapper);
	}

	@Test
	public void shouldNotCreateSound_whenSoundIsOnList() {
		// given
		String filepath = "test.mp3";

		// when;
		addSound(filepath);
		addSound(filepath);

		// than
		verify(mimeSourceProvider, times(1)).getSourcesWithTypeByExtension(filepath);
		verify(mediaWrapperCreator, times(1)).createMediaWrapper(eq(filepath), eq(sourceWithType), callbackReceiverCaptor.capture());
	}

	@Test
	public void shouldReturnAllSounds() {
		// given
		addSound("test.mp3");

		// when
		Collection<MediaWrapper<Widget>> result = testObj.getAllSounds();

		// than
		assertThat(result.size()).isEqualTo(1);
	}

	@Test
	public void shouldReturnTrue_whenContainsSound() {
		// given
		addSound("test.mp3");

		// when
		boolean result = testObj.containsWrapper(mediaWrapper);

		// than
		assertThat(result).isTrue();
	}

	@Test
	public void shouldReturnFalse_whenDoesNotContainsSound() {
		// given
		addSound("test.mp3");
		MediaWrapper<Widget> otherMediaWrapper = mock(MediaWrapper.class);

		// when
		boolean result = testObj.containsWrapper(otherMediaWrapper);

		// than
		assertThat(result).isFalse();

	}

	private void addSound(String filepath) {
		sourceWithType.put(filepath, "audio/mp4");
		when(mimeSourceProvider.getSourcesWithTypeByExtension(filepath)).thenReturn(sourceWithType);
		testObj.initSound(filepath);

		verify(mimeSourceProvider).getSourcesWithTypeByExtension(filepath);
		verify(mediaWrapperCreator).createMediaWrapper(eq(filepath), eq(sourceWithType), callbackReceiverCaptor.capture());

		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);
	}
}
