package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.hack.PageChangePauseHandlerAdder;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPlayerAttacher {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoBean videoBean;
	private final VideoView view;
	private final PageChangePauseHandlerAdder pageChangePauseHandlerAdder;

	@Inject
	public VideoPlayerAttacher(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, @ModuleScoped VideoView view,
			PageChangePauseHandlerAdder pageChangePauseHandlerAdder) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoBean = videoBean;
		this.view = view;
		this.pageChangePauseHandlerAdder = pageChangePauseHandlerAdder;
	}

	public void attachNew() {
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);
		
		VideoPlayerControl control = videoPlayer.getController();
		pageChangePauseHandlerAdder.registerPauseOnPageChange(control);
		
		view.attachVideoPlayer(videoPlayer);
	}
}
