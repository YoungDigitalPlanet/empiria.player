package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private VideoView view;

	@Inject
	public VideoPresenter(@ModuleScoped VideoView view) {
		this.view = view;
	}

	public void start() {
		view.createView();
	}

	public Widget getView() {
		return view.asWidget();
	}
}
