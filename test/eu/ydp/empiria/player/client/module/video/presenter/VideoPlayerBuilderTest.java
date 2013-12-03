package eu.ydp.empiria.player.client.module.video.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.hack.VideoPlayerPauseOnPageChangeHandler;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(VideoPlayer.class)
public class VideoPlayerBuilderTest {

	@InjectMocks
	private VideoPlayerBuilder testObj;
	@Mock
	private VideoPlayerFactory factory;
	@Mock
	private VideoBean videoBean;
	
	@Mock
	private VideoView view;
	@Mock
	private EventsBus eventsBus;
	
	
	@BeforeClass
	public static void before() {
		GWTMockUtilities.disarm();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateVideoPlayer() {
		// given
		VideoPlayerControl playerControl = mock(VideoPlayerControl.class);
		VideoPlayer videoPlayer = mock(VideoPlayer.class);
		when(videoPlayer.getControl()).thenReturn(playerControl);
		when(factory.create(videoBean)).thenReturn(videoPlayer);

		// when
		testObj.buildVideoPlayer();

		// then
		verify(eventsBus).addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(VideoPlayerPauseOnPageChangeHandler.class));
		verify(videoPlayer).addAttachHandler(any(Handler.class));
	}

	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}
}
