package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.VideoElement;

import eu.ydp.empiria.player.client.module.video.model.VideoModel;

public class VideoPlayerFactory {
	public VideoPlayer create(VideoModel videoJsModel) {
		VideoPlayer videoJsPlayer = new VideoPlayer(videoJsModel.getWidth(), videoJsModel.getHeight());
		videoJsPlayer.addSource(videoJsModel.getSrc(), VideoElement.TYPE_MP4);
		videoJsPlayer.setPoster(videoJsModel.getPoster());

		return videoJsPlayer;
	}
}
