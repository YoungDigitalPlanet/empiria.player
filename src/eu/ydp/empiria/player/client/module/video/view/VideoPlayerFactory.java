package eu.ydp.empiria.player.client.module.video.view;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;

public class VideoPlayerFactory {

	@Inject
	Provider<VideoPlayer> videoPlayerProvider;

	public VideoPlayer create(VideoBean videoBean) {
		VideoPlayer videoPlayer = videoPlayerProvider.get();
		videoPlayer.setWidth(videoBean.getHeight());
		videoPlayer.setHeight(videoBean.getHeight());
		videoPlayer.setPoster(videoBean.getPoster());
		videoPlayer.addSources(videoBean.getSources());
		return videoPlayer;
	}
}
