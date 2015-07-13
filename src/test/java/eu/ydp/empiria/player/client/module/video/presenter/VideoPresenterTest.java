package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.VideoPlayerForBookshelfOnAndroid;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPresenterTest {

    @InjectMocks
    private VideoPresenter testObj;
    @Mock
    private VideoPlayerReattacher videoPlayerReattacher;
    @Mock
    private VideoPlayerBuilder videoPlayerBuilder;
    @Mock
    private VideoView view;
    @Mock
    private VideoPlayer videoPlayer;
    @Mock
    private UserAgentUtil userAgentUtil;
    @Mock
    private VideoModuleFactory videoModuleFactory;
    @Mock
    private VideoPlayerForBookshelfOnAndroid videoPlayerForBookshelfOnAndroid;

    @Before
    public void init() {
        when(videoPlayerBuilder.build()).thenReturn(videoPlayer);
        when(videoModuleFactory.createVideoPlayerForBookshelf(videoPlayer)).thenReturn(videoPlayerForBookshelfOnAndroid);
    }

    @Test
    public void shouldCreateViewAndApplyHackWhenStart() {
        // given
        VideoPlayer videoPlayer = mock(VideoPlayer.class);
        when(videoPlayerBuilder.build()).thenReturn(videoPlayer);

        // when
        testObj.start();

        // then
        verify(view).createView();
        verify(view).attachVideoPlayer(isA(VideoPlayer.class));
        verify(videoPlayerReattacher).registerReattachHandlerToView(view);
    }

    @Test
    public void shouldInitVideoForBookshelfWhenIsOnAndroidAndAIR() {
        // given
        when(userAgentUtil.isAndroidBrowser()).thenReturn(true);
        when(userAgentUtil.isAIR()).thenReturn(true);

        //when
        testObj.start();

        //then
        verify(videoPlayerForBookshelfOnAndroid).init(view);
    }
}
