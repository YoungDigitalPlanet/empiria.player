package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;

public class PicturePlayerPresenter {

	private PicturePlayerView view;
	private PicturePlayerFullscreenController fullscreenController;
	private PicturePlayerBean bean;

	private boolean template = false;

	@Inject
	public PicturePlayerPresenter(PicturePlayerView view, PicturePlayerFullscreenController fullscreenController) {
		this.view = view;
		this.fullscreenController = fullscreenController;
	}

	public void init(PicturePlayerBean bean) {
		this.bean = bean;

		view.setPresenter(this);
		view.setImage(bean.getTitle(), bean.getSrc());
		initFullScreenMediaButton(bean);
	}

	private void initFullScreenMediaButton(PicturePlayerBean bean) {
		if (isFullscreenSupported(bean)) {
			view.addFullscreenButton();
		}
	}

	private boolean isFullscreenSupported(PicturePlayerBean bean) {
		return bean.hasFullscreen() && !template;
	}

	public void openFullscreen() {
		fullscreenController.openFullscreen(bean);
	}

	public Widget getView() {
		return view.asWidget();
	}

	public void setTemplate(boolean template) {
		this.template = template;
	}
}