package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;

public class VideoPlayerFactory {

	private final Provider<VideoPlayer> videoPlayerProvider;

	@Inject
	public VideoPlayerFactory(Provider<VideoPlayer> videoPlayerProvider) {
		this.videoPlayerProvider = videoPlayerProvider;
	}

	public VideoPlayer create(VideoBean videoBean) {
		VideoPlayer videoPlayer = videoPlayerProvider.get();
		videoPlayer.setWidth(videoBean.getWidth());
		videoPlayer.setHeight(videoBean.getHeight());
		videoPlayer.setPoster(videoBean.getPoster());
		videoPlayer.addSources(videoBean.getSources());
		return videoPlayer;
	}
}
