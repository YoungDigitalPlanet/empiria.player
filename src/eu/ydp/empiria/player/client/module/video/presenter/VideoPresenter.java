package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoBean videoBean;
	private  VideoView view;
	
	private FlowPanel container;

	@Inject
	public VideoPresenter(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, @ModuleScoped VideoView view) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoBean = videoBean;
		this.view = view;
		
		container = new FlowPanel();
	}

	public void start() {
		view = new VideoViewImpl();
		
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);
		videoPlayer.callback(new ICalbackVideo() {
			public void onUnLoad() {
				start();
			}
		});
		view.createView(videoPlayer);
		
		container.clear();
		container.add(view);
	}

	public Widget getView() {
//		return view.asWidget();
		return container;
	}
}
