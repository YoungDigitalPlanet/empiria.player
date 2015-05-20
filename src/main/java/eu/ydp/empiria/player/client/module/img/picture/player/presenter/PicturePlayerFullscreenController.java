package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.lightbox.*;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;

public class PicturePlayerFullscreenController {

	private FullScreenModeProvider fullScreenModeProvider;

	@Inject
	public PicturePlayerFullscreenController(FullScreenModeProvider fullScreenModeProvider) {
		this.fullScreenModeProvider = fullScreenModeProvider;
	}

	public void openFullScreen(PicturePlayerBean bean) {
		FullScreen fullScreen = fullScreenModeProvider.getFullscreen(bean.getFullscreenMode());
		fullScreen.openImage(bean.getSrcFullScreen(), bean.getTitle());
	}
}