package eu.ydp.empiria.player.client.module.video.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.VideoElementWrapperBuilder;
import eu.ydp.empiria.player.client.module.video.structure.SourceBean;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoElementWrapper;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;

@RunWith(MockitoJUnitRunner.class)
public class VideoPlayerFactoryTest {

	@InjectMocks
	private VideoPlayerFactory playerFactory;
	@Mock
	private Provider<VideoElementWrapperBuilder> elementBuilderProvider;
	@Mock
	private VideoModuleFactory videoModuleFactory;

	@Test
	public void shouldCreatePlayerWithVideoElement() {
		// given
		final int WIDTH = 21;
		final int HEIGHT = 10;
		final String POSTER = "POSTER";
		ArrayList<SourceBean> SOURCES = Lists.newArrayList(mock(SourceBean.class));
		VideoBean videoBean = new VideoBean();
		videoBean.setWidth(WIDTH);
		videoBean.setHeight(HEIGHT);
		videoBean.setPoster(POSTER);
		videoBean.setSources(SOURCES);

		VideoPlayer videoPlayer = mock(VideoPlayer.class);

		VideoElementWrapper videoElement = mock(VideoElementWrapper.class);
		VideoElementWrapperBuilder videoElementBuilder = mock(VideoElementWrapperBuilder.class, new SelfReturningAnswer());
		when(videoElementBuilder.build()).thenReturn(videoElement);

		when(videoModuleFactory.createVideoPlayer(videoElement)).thenReturn(videoPlayer);

		when(elementBuilderProvider.get()).thenReturn(videoElementBuilder);

		// when
		playerFactory.create(videoBean);

		// then
		verify(videoElementBuilder).withWidth(WIDTH);
		verify(videoElementBuilder).withHeight(HEIGHT);
		verify(videoElementBuilder).withPoster(POSTER);
		verify(videoElementBuilder).withSources(SOURCES);
		verify(videoElementBuilder).build();
		verify(videoModuleFactory).createVideoPlayer(videoElement);
	}

	class SelfReturningAnswer implements Answer<Object> {

		public Object answer(InvocationOnMock invocation) throws Throwable {
			Object mock = invocation.getMock();
			if (invocation.getMethod().getReturnType().isInstance(mock)) {
				return mock;
			}
			return null;
		}
	}
}
