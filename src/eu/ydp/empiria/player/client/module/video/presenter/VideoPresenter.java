package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.VideoPlayerForBookshelf;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class VideoPresenter {

	private final VideoView view;
	private final VideoPlayerReattacher reAttachHack;
	private final VideoPlayerBuilder videoPlayerBuilder;
	private final VideoPlayerForBookshelf videoPlayerForBookshelf;
	private final UserAgentUtil userAgentUtil;


	@Inject
	public VideoPresenter(@ModuleScoped VideoView view, @ModuleScoped VideoPlayerBuilder videoPlayerAttacher, @ModuleScoped VideoPlayerReattacher reAttachHack,
			UserAgentUtil userAgentUtil, VideoModuleFactory videoModuleFactory) {
		this.view = view;
		this.videoPlayerBuilder = videoPlayerAttacher;
		this.reAttachHack = reAttachHack;
		this.userAgentUtil = userAgentUtil;
		this.videoPlayerForBookshelf = videoModuleFactory.createVideoPlayerForBookshelf(view);
	}

	public void start() {
		view.createView();

		VideoPlayer videoPlayer = videoPlayerBuilder.build();
		view.attachVideoPlayer(videoPlayer);
		reAttachHack.registerReattachHandlerToView(view);
		initVideoForBookshelf();
	}

	private void initVideoForBookshelf() {
		if (userAgentUtil.isAndroidBrowser() && userAgentUtil.isAIR()) {
			videoPlayerForBookshelf.init();
		}
	}

	public Widget getView() {
		return view.asWidget();
	}
}
