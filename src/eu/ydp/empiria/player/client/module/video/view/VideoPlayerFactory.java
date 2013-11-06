package eu.ydp.empiria.player.client.module.video.view;

import eu.ydp.empiria.player.client.module.video.structure.SourceBean;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;

public class VideoPlayerFactory {

	public VideoPlayer create(VideoBean videoBean) {
		VideoPlayer videoPlayer = new VideoPlayer(videoBean.getWidth(), videoBean.getHeight());
		videoPlayer.setPoster(videoBean.getPoster());
		for (SourceBean source : videoBean.getSources()) {
			videoPlayer.addSource(source.getSrc(), source.getType());
		}
		return videoPlayer;
	}
}
