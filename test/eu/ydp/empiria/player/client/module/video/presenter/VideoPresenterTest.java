package eu.ydp.empiria.player.client.module.video.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;

import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPresenterTest {

	@InjectMocks
	private VideoPresenter presenter;
	@Mock
	private VideoPlayerReattacher videoPlayerReattacher;
	@Mock
	private VideoPlayerBuilder videoPlayerBuilder;
	@Mock
	private VideoView view;
	@Mock
	private UserAgentUtil userAgentUtil;

	@Captor
	private ArgumentCaptor<Command> commandCaptor;

	@Test
	public void shouldCreateViewAndApplyHackWhenStart() {
		// given
		VideoPlayer videoPlayer = mock(VideoPlayer.class);
		when(videoPlayerBuilder.build()).thenReturn(videoPlayer);

		// when
		presenter.start();

		// then
		verify(view).createView();
		verify(view).attachVideoPlayer(isA(VideoPlayer.class));
		verify(videoPlayerReattacher).registerReattachHandlerToView(view);
	}
	//
	// @Test
	// public void shouldDelegatePlayToJSWhenIsOnAndroidAndAIR() {
	// // given
	// String PLAYER_ID = "PLAYER_ID";
	// when(view.getPlayerId()).thenReturn(PLAYER_ID);
	//
	// when(userAgentUtil.isAndroidBrowser()).thenReturn(true);
	// when(userAgentUtil.isAIR()).thenReturn(true);
	//
	// presenter.start();
	// verify(view).preparePlayDelegationToJS(commandCaptor.capture());
	//
	// // when
	// commandCaptor.getValue().execute(null);
	// }
}
