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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.shared.HandlerRegistration;
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
	@Mock
	private HandlerRegistration handlerRegistration;

	private Handler handler;

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

	@Test
	public void shouldRemoveTheHandlerOnDetach() {
		// given
		VideoPlayer videoPlayer = mock(VideoPlayer.class);
		when(eventsBus.addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(VideoPlayerPauseOnPageChangeHandler.class))).thenReturn(
				handlerRegistration);
		when(factory.create(videoBean)).thenReturn(videoPlayer);
		doAnswer(answer()).when(videoPlayer).addAttachHandler(any(Handler.class));

		AttachEvent event = mock(AttachEvent.class);
		when(event.isAttached()).thenReturn(Boolean.FALSE);

		// when
		testObj.buildVideoPlayer();
		handler.onAttachOrDetach(event);

		// then
		verify(handlerRegistration).removeHandler();
	}

	@Test
	public void shouldNotRemoveTheHandlerOnDetach() {
		// given
		VideoPlayer videoPlayer = mock(VideoPlayer.class);
		when(eventsBus.addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(VideoPlayerPauseOnPageChangeHandler.class))).thenReturn(
				handlerRegistration);
		when(factory.create(videoBean)).thenReturn(videoPlayer);
		doAnswer(answer()).when(videoPlayer).addAttachHandler(any(Handler.class));

		AttachEvent event = mock(AttachEvent.class);
		when(event.isAttached()).thenReturn(Boolean.TRUE);

		// when
		testObj.buildVideoPlayer();
		handler.onAttachOrDetach(event);

		// then
		verify(handlerRegistration, never()).removeHandler();
	}

	private Answer<Void> answer() {
		return new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				handler = (Handler) invocation.getArguments()[0];
				return null;
			}

		};

	}

	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}
}
