package eu.ydp.empiria.player.client.module.slideshow.sound;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.*;
import eu.ydp.empiria.player.client.module.slideshow.SlideEnd;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.*;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import java.util.Collection;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlideSoundControllerTest {

	@InjectMocks
	private SlideSoundController testObj;
	@Mock
	private MediaWrapperController mediaWrapperController;
	@Mock
	private SlideSounds slideSounds;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private SlideEnd command;
	@Mock
	private Optional<SlideEnd> audioEnd;
	@Mock
	private MediaWrapper<Widget> sound;
	@Mock
	private MediaEvent mediaEvent;
	private final String filepath = "test.mp3";

	@Before
	public void init() {
		when(slideSounds.getSound(filepath)).thenReturn(sound);
	}
	
	@Test
	public void shouldInitSounds() {
		// given

		// when
		testObj.initSound(filepath);
		testObj.initSound(filepath);

		// then
		verify(slideSounds, times(2)).initSound(filepath);
	}

	@Test
	public void shouldAddHandlerOnInit() {
		// given
		Type<MediaEventHandler, MediaEventTypes> eventType = MediaEvent.getType(MediaEventTypes.ON_END);
		
		// than
		verify(eventsBus).addHandler(eventType, testObj);
	}

	@Test
	public void shouldPlayNewSound() {
		// given

		// when
		testObj.playSound(filepath, command);

		// than
		verify(mediaWrapperController).play(sound);
	}


	@Test
	public void shouldPauseSound() {
		// given

		// when
		testObj.pauseSound(filepath);

		// than
		verify(mediaWrapperController).pause(sound);
	}
	
	@Test
	public void shouldStopAllSounds() {
		// given
		Collection<MediaWrapper<Widget>> sounds = Lists.newArrayList();
		sounds.add(sound);
		sounds.add(sound);
		when(slideSounds.getAllSounds()).thenReturn(sounds);

		// when
		testObj.stopAllSounds();

		// than
		verify(mediaWrapperController, times(2)).stop(sound);
	}

	@Test
	public void shouldExecuteAudioEnd_whenSlideSoundsContainsWrapper() {
		// given
		when(slideSounds.containsWrapper(any(MediaWrapper.class))).thenReturn(true);
		testObj.playSound(filepath, command);

		// when
		testObj.onMediaEvent(mediaEvent);

		// than
		verify(command).onEnd();
	}
	
	@Test
	public void shouldNotExecuteAudioEnd_andSlideSoundDoesNotContainWrapper() {
		// given
		when(slideSounds.containsWrapper(any(MediaWrapper.class))).thenReturn(false);
		testObj.playSound(filepath, command);

		// when
		testObj.onMediaEvent(mediaEvent);

		// than
		verify(command, never()).onEnd();

	}
}