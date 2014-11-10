package eu.ydp.empiria.player.client.module.video;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPlayerForBookshelfOnAndroidTest {

	private VideoPlayerForBookshelfOnAndroid testObj;
	@Mock
	private VideoView view;
	@Mock
	private VideoPlayer videoPlayer;
	@Mock
	private ExternalFullscreenVideoConnector externalFullscreenVideoConnector;

	@Captor
	private ArgumentCaptor<Command> commandCaptor;
	@Captor
	private ArgumentCaptor<List<String>> listCaptor;

	@Test
	public void shouldOpenFullscreen() {
		//given
		String source = "video_source";
		String playerId = "player_id";

		when(videoPlayer.getSource()).thenReturn(source);
		when(videoPlayer.getId()).thenReturn(playerId);

		testObj = new VideoPlayerForBookshelfOnAndroid(videoPlayer, externalFullscreenVideoConnector);
		testObj.init(view);

		verify(view).preparePlayDelegationToJS(commandCaptor.capture());

		//when
		commandCaptor.getValue().execute(null);

		//then
		verify(externalFullscreenVideoConnector).openFullscreen(eq(playerId), listCaptor.capture(), eq(0.0));
		assertThat(listCaptor.getValue().get(0)).isEqualTo(source);
	}
}
