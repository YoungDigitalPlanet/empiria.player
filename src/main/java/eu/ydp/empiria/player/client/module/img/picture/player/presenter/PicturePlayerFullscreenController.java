package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.*;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;

public class PicturePlayerFullscreenController {

	private LightBoxProvider lightBoxProvider;
	private UserAgentCheckerWrapper userAgentCheckerWrapper;
	private PicturePlayerFullscreenDelay fullscreenDelay;

	@Inject
	public PicturePlayerFullscreenController(LightBoxProvider lightBoxProvider, UserAgentCheckerWrapper userAgentCheckerWrapper,
			PicturePlayerFullscreenDelay fullscreenDelay) {
		this.lightBoxProvider = lightBoxProvider;
		this.userAgentCheckerWrapper = userAgentCheckerWrapper;
		this.fullscreenDelay = fullscreenDelay;
	}

	public void openFullscreen(PicturePlayerBean bean) {
		LightBox lightBox = lightBoxProvider.getFullscreen(bean.getFullscreenMode());

		if (userAgentCheckerWrapper.isStackAndroidBrowser()) {
			fullscreenDelay.openImageWithDelay(lightBox, bean);
		} else {
			lightBox.openImage(bean.getSrcFullScreen(), bean.getTitle());
		}
	}
}