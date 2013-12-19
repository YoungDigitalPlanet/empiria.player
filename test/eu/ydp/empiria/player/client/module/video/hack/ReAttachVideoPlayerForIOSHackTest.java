package eu.ydp.empiria.player.client.module.video.hack;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerBuilder;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

public class ReAttachVideoPlayerForIOSHackTest {

	@InjectMocks
	private ReAttachVideoPlayerForIOSHack hack;
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

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		preparePageScope();
		prepareHandler();
	}

	@Test
	public void shouldNotAttachNewVideoPlayer() {
		// given
		hack.apply(view);

		// when
		playerEventHandler.onPlayerEvent(null);

		// then
		verify(videoPlayerBuilder, never()).buildVideoPlayer();
	}

	@Test
	public void shouldAtachNewVideoPlayer() {
		// given
		hack.apply(view);

		// when
		playerEventHandler.onPlayerEvent(null);
		playerEventHandler.onPlayerEvent(null);

		// then
		verify(videoPlayerBuilder).buildVideoPlayer();
	}

	@Test
	public void shouldAtachNewVideoPlayerTwoTimes() {
		// given
		hack.apply(view);

		// when
		playerEventHandler.onPlayerEvent(null);
		playerEventHandler.onPlayerEvent(null);
		playerEventHandler.onPlayerEvent(null);

		// then
		verify(videoPlayerBuilder, times(2)).buildVideoPlayer();
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
}
