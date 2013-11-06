package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	@Inject
	@ModuleScoped
	private VideoView view;
	@Inject
	@ModuleScoped
	private VideoBean bean;

	public void start() {
		view.createView(bean);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
