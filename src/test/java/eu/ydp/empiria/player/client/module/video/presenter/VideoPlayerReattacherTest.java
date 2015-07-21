package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPlayerReattacherTest {

    @InjectMocks
    private VideoPlayerReattacher testObj;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private VideoPlayerBuilder videoPlayerBuilder;
    @Mock
    private PageScopeFactory pageScopeProvider;
    @Mock
    private VideoView view;

    private PlayerEventHandler playerEventHandler;
    private CurrentPageScope currentPageScope;

    @Before
    public void setUp() {
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
        verify(view).attachVideoPlayer(mockPlayer);
    }

    private void preparePageScope() {
        currentPageScope = mock(CurrentPageScope.class);
        when(pageScopeProvider.getCurrentPageScope()).thenReturn(currentPageScope);
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
        doAnswer(answer).when(eventsBus)
                .addHandler(eq(eventType), any(PlayerEventHandler.class), eq(currentPageScope));
    }
}
