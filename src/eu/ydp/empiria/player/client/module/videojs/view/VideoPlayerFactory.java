package eu.ydp.empiria.player.client.module.videojs.view;

import com.google.gwt.dom.client.VideoElement;

import eu.ydp.empiria.player.client.module.videojs.model.VideoJsModel;

public class VideoPlayerFactory {
	public VideoJsPlayer create(VideoJsModel videoJsModel) {
		VideoJsPlayer videoJsPlayer = new VideoJsPlayer(videoJsModel.getWidth(), videoJsModel.getHeight());
		videoJsPlayer.addSource(videoJsModel.getSrc(), VideoElement.TYPE_MP4);
		videoJsPlayer.setPoster(videoJsModel.getPoster());

		return videoJsPlayer;
	}
}
