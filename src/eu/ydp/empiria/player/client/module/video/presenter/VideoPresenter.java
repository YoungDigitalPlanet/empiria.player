package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.model.VideoModel;
import eu.ydp.empiria.player.client.module.video.view.VideoView;

public class VideoPresenter {

	@Inject
	private VideoView view;

	public void start(VideoModel videoJsModel) {
		view.createView(videoJsModel);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
