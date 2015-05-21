package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.*;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;

public class PicturePlayerFullscreenController {

	private LightBoxProvider lightBoxProvider;

	@Inject
	public PicturePlayerFullscreenController(LightBoxProvider lightBoxProvider) {
		this.lightBoxProvider = lightBoxProvider;
	}

	public void openFullScreen(PicturePlayerBean bean) {
		LightBox lightBox = lightBoxProvider.getFullscreen(bean.getFullscreenMode());
		lightBox.openImage(bean.getSrcFullScreen(), bean.getTitle());
	}
}