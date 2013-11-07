package eu.ydp.empiria.player.client.module.video.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;

@RunWith(MockitoJUnitRunner.class)
public class VideoPresenterTest {

	@InjectMocks
	private VideoPresenter presenter;
	@Mock
	private VideoPlayerFactory videoPlayerFactory;
	@Mock
	private VideoBean videoBean;
	@Mock
	private VideoView view;

	
	@Test
	public void shouldCreateViewWithPlayer() {
		// given
		VideoPlayer player = mock(VideoPlayer.class);
		when(videoPlayerFactory.create(videoBean)).thenReturn(player);

		// when
		presenter.start();

		// then
		verify(view).createView(player);
	}
}
