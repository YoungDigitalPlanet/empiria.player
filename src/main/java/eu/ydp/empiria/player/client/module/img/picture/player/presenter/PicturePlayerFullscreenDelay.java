package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.gwt.user.client.Timer;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;

public class PicturePlayerFullscreenDelay {

	public void openImageWithDelay(final LightBox lightBox, final PicturePlayerBean bean) {
		Timer timer = createFullscreenDelay(lightBox, bean);
		timer.schedule(300);
	}

	private Timer createFullscreenDelay(final LightBox lightBox, final PicturePlayerBean bean) {
		return new Timer() {
			@Override public void run() {
				lightBox.openImage(bean.getSrcFullScreen(), bean.getTitle());
			}
		};
	}
}
