package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSChecker;
import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSHack;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private final VideoView view;
	private final ReAttachVideoPlayerForIOSHack reAttachHack;
	private final VideoPlayerAttacher videoPlayerAttacher;
	private final ReAttachVideoPlayerForIOSChecker hackChecker;

	@Inject
	public VideoPresenter(@ModuleScoped VideoView view, @ModuleScoped VideoPlayerAttacher videoPlayerAttacher, ReAttachVideoPlayerForIOSChecker hackChecker,
			@ModuleScoped ReAttachVideoPlayerForIOSHack reAttachHack) {
		this.view = view;
		this.videoPlayerAttacher = videoPlayerAttacher;
		this.hackChecker = hackChecker;
		this.reAttachHack = reAttachHack;
	}

	public void start() {
		view.createView();
		videoPlayerAttacher.attachNew();

		if (hackChecker.isNeeded()) {
			reAttachHack.apply();
		}
	}

	public Widget getView() {
		return view.asWidget();
	}
}
