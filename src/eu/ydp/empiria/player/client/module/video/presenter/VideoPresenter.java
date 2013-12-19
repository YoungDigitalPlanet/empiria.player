package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSChecker;
import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSHack;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private final VideoView view;
	private final ReAttachVideoPlayerForIOSHack reAttachHack;
	private final VideoPlayerBuilder videoPlayerBuilder;
	private final ReAttachVideoPlayerForIOSChecker iOSChecker;

	@Inject
	public VideoPresenter(@ModuleScoped VideoView view, @ModuleScoped VideoPlayerBuilder videoPlayerAttacher,
			@ModuleScoped ReAttachVideoPlayerForIOSHack reAttachHack, ReAttachVideoPlayerForIOSChecker iOSChecker) {
		this.view = view;
		this.videoPlayerBuilder = videoPlayerAttacher;
		this.reAttachHack = reAttachHack;
		this.iOSChecker = iOSChecker;
	}

	public void start() {
		view.createView();
		
		VideoPlayer videoPlayer = videoPlayerBuilder.buildVideoPlayer();
		view.attachVideoPlayer(videoPlayer);

		if (iOSChecker.isNeeded()) {
			reAttachHack.apply(view);
		}
	}

	public Widget getView() {
		return view.asWidget();
	}
}
