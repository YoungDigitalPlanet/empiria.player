package eu.ydp.empiria.player.client.module.video.presenter;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.structure.SourceBean;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;

@RunWith(MockitoJUnitRunner.class)
public class VideoPlayerFactoryTest {

	@InjectMocks
	private VideoPlayerFactory playerFactory;
	@Mock
	private Provider<VideoPlayer> videoPlayerProvider;

	@Test
	public void shouldTestName() {
		// given
		final int WIDTH = 21;
		final int HEIGHT = 10;
		final String POSTER = "PSOTER";
		ArrayList<SourceBean> SOURCES = Lists.newArrayList(mock(SourceBean.class));
		VideoBean videoBean = new VideoBean();
		videoBean.setWidth(WIDTH);
		videoBean.setHeight(HEIGHT);
		videoBean.setPoster(POSTER);
		videoBean.setSources(SOURCES);

		VideoPlayer playerMock = mock(VideoPlayer.class);
		when(videoPlayerProvider.get()).thenReturn(playerMock);

		// when
		VideoPlayer player = playerFactory.create(videoBean);

		// then
		assertThat(player).isEqualTo(playerMock);
		verify(player).setWidth(WIDTH);
		verify(player).setHeight(HEIGHT);
		verify(player).setPoster(POSTER);
		verify(player).addSources(SOURCES);
	}
}
