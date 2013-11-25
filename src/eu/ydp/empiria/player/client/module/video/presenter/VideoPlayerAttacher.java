package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPlayerAttacher {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoBean videoBean;
	private final VideoView view;

	@Inject
	public VideoPlayerAttacher(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, @ModuleScoped VideoView view) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoBean = videoBean;
		this.view = view;
	}

	public void attachNew() {
		attachNewVideoPlayer(false);
	}

	public void attachNewWithReattachHack() {
		attachNewVideoPlayer(true);
	}

	private void attachNewVideoPlayer(boolean withHack) {
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);
		if (withHack) {
			videoPlayer.activateReAttachHack();
		}
		view.attachVideoPlayer(videoPlayer);
	}
}
