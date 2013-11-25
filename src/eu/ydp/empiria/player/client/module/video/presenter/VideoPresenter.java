package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSHack;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private final VideoView view;
	private final ReAttachVideoPlayerForIOSHack reAttachHack;
	private final VideoPlayerAttacher videoPlayerAttacher;

	@Inject
	public VideoPresenter(@ModuleScoped VideoView view, @ModuleScoped VideoPlayerAttacher videoPlayerAttacher,
			@ModuleScoped ReAttachVideoPlayerForIOSHack reAttachHack) {
		this.view = view;
		this.videoPlayerAttacher = videoPlayerAttacher;
		this.reAttachHack = reAttachHack;
	}

	public void start() {
		view.createView();
		if (reAttachHack.isNeeded()) {
			videoPlayerAttacher.attachNewWithReattachHack();
			reAttachHack.apply();
		} else {
			videoPlayerAttacher.attachNew();
		}
	}

	public Widget getView() {
		return view.asWidget();
	}
}
