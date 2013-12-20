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

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerBuilder;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerReattacher;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(VideoPlayer.class)
public class VideoPlayerReattacherTest {

	@InjectMocks
	private VideoPlayerReattacher testObj;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private VideoPlayerBuilder videoPlayerBuilder;
	@Mock
	private Provider<CurrentPageScope> pageScopeProvider;
	@Mock
	private VideoView view;

	private PlayerEventHandler playerEventHandler;
	private CurrentPageScope currentPageScope;

	@BeforeClass
	public static void before() {
		GWTMockUtilities.disarm();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		preparePageScope();
		prepareHandler();
	}

	@Test
	public void shouldAtachNewVideoPlayer() {
		// given
		testObj.registerReattachHandlerToView(view);
		final VideoPlayer mockPlayer = mock(VideoPlayer.class);
		when(videoPlayerBuilder.build()).thenReturn(mockPlayer);

		// when
		playerEventHandler.onPlayerEvent(null);

		// then
		verify(videoPlayerBuilder).build();
		verify(view).attachVideoPlayer(mockPlayer);
	}

	private void preparePageScope() {
		currentPageScope = mock(CurrentPageScope.class);
		when(pageScopeProvider.get()).thenReturn(currentPageScope);
	}

	private void prepareHandler() {
		Type<PlayerEventHandler, PlayerEventTypes> eventType = PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED);

		Answer<Void> answer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				playerEventHandler = (PlayerEventHandler) invocation.getArguments()[1];
				return null;
			}

		};
		doAnswer(answer).when(eventsBus).addHandler(eq(eventType), any(PlayerEventHandler.class), eq(currentPageScope));
	}

	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}

}
