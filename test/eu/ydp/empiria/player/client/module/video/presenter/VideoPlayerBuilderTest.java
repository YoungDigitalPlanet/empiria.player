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

import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(VideoPlayer.class)
public class VideoPlayerBuilderTest {

	@InjectMocks
	private VideoPlayerBuilder testObj;
	@Mock
	private VideoPlayerFactory playerPactory;
	@Mock
	private VideoModuleFactory moduleFactory; 
	@Mock
	private VideoBean videoBean;

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
		VideoPlayer videoPlayer = mock(VideoPlayer.class);
		when(playerPactory.create(videoBean)).thenReturn(videoPlayer);
		
		final VideoPlayerAttachHandler mockHandler = mock(VideoPlayerAttachHandler.class);
		when(moduleFactory.createAttachHandlerForRegisteringPauseEvent(videoPlayer)).thenReturn(mockHandler);

		// when
		testObj.buildVideoPlayer();

		// then
		verify(moduleFactory).createAttachHandlerForRegisteringPauseEvent(videoPlayer);
		verify(videoPlayer).addAttachHandler(mockHandler);

	}

	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}
}
