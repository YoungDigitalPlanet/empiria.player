package eu.ydp.empiria.player.client.module.videojs.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.videojs.model.VideoJsModel;
import eu.ydp.empiria.player.client.module.videojs.view.VideoJsView;

public class VideoJsPresenter {

	@Inject
	private VideoJsView view;

	public void start(VideoJsModel videoJsModel) {
		view.createView(videoJsModel);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
