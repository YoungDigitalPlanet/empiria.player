package eu.ydp.empiria.player.client.module.img.picture.player.presenter.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.lightbox.*;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;

public class FullscreenButtonPresenter {

	private FullScreenModeProvider fullScreenModeProvider;

	private String imageUrl = null;
	private String title = null;

	private FullScreen fullScreen;

	@Inject
	public FullscreenButtonPresenter(FullScreenModeProvider fullScreenModeProvider) {
		this.fullScreenModeProvider = fullScreenModeProvider;
	}

	public void init(PicturePlayerBean bean) {
		String mode = bean.getFullscreenMode();
		if (bean.hasTitle()) {
			title = bean.getImgTitleBean().getTitleName();
		}

		this.imageUrl = bean.getSrcFullScreen();
		this.fullScreen = fullScreenModeProvider.getFullscreen(mode);
	}

	public void openFullScreen() {
		fullScreen.openImage(imageUrl, title);
	}
}
