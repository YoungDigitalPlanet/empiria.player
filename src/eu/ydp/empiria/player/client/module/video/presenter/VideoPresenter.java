package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoBean videoBean;
	private final VideoView view;

	@Inject
	public VideoPresenter(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, @ModuleScoped VideoView view) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoBean = videoBean;
		this.view = view;
	}

	public void start() {
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);
		view.createView(videoPlayer);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
