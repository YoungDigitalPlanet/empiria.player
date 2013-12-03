package eu.ydp.empiria.player.client.module.video.presenter;

import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.hack.PageChangePauseHandlerAdder;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(VideoPlayer.class)
public class VideoPlayerAttacherTest {

	@InjectMocks
	private VideoPlayerAttacher attacher;
	@Mock
	private VideoPlayerFactory factory;
	@Mock
	private VideoBean videoBean;
	@Mock
	private VideoView view;
	@Mock
	private PageChangePauseHandlerAdder pageChangeHandlerAdder;
	
	
	@BeforeClass
	public static void before() {
		GWTMockUtilities.disarm();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateAndAttachVideoPlayer() {
		// given
		VideoPlayerControl playerControl = mock(VideoPlayerControl.class);
		VideoPlayer videoPlayer = mock(VideoPlayer.class);
		when(videoPlayer.getController()).thenReturn(playerControl);
		when(factory.create(videoBean)).thenReturn(videoPlayer);

		// when
		attacher.attachNew();

		// then
		verify(view).attachVideoPlayer(videoPlayer);
		verify(pageChangeHandlerAdder).registerPauseOnPageChange(playerControl);
	}

	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}
}
