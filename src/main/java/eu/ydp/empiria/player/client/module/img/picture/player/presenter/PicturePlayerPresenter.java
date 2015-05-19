package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.button.FullscreenButtonPresenter;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerViewImpl;

public class PicturePlayerPresenter {

	private PicturePlayerViewImpl view;
	private FullscreenButtonPresenter fullscreenButtonPresenter;

	@Inject
	public PicturePlayerPresenter(PicturePlayerViewImpl view, FullscreenButtonPresenter fullscreenButtonPresenter) {
		this.view = view;
		this.fullscreenButtonPresenter = fullscreenButtonPresenter;
	}

	public void init(PicturePlayerBean bean) {
		String title = getTitle(bean);

		view.setImage(title, bean.getSrc());
		initFullScreenMediaButton(bean);
	}

	private String getTitle(PicturePlayerBean bean) {
		String title = "";
		if (bean.hasTitle()) {
			title = bean.getImgTitleBean().getTitleName();
		}
		return title;
	}

	private void initFullScreenMediaButton(PicturePlayerBean bean) {
		if (bean.hasFullscreen()) {
			fullscreenButtonPresenter.init(bean);
			view.setClickHandler(onOpenFullscreen());
		}
	}

	private ClickHandler onOpenFullscreen() {
		return new ClickHandler() {
			@Override public void onClick(ClickEvent event) {
				fullscreenButtonPresenter.openFullScreen();
			}
		};
	}

	public Widget getView() {
		return view.asWidget();
	}
}