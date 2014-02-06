package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPlayerBuilder {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoModuleFactory videoModuleFactory;
	private final VideoBean videoBean;

	@Inject
	public VideoPlayerBuilder(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, VideoModuleFactory videoModuleFactory) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoModuleFactory = videoModuleFactory;
		this.videoBean = videoBean;
	}

	public VideoPlayer build() {
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);

		final VideoPlayerAttachHandler handler = videoModuleFactory.createAttachHandlerForRegisteringPauseEvent(videoPlayer);
		videoPlayer.addAttachHandler(handler);

		return videoPlayer;
	}

}
