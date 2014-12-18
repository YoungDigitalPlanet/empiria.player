package eu.ydp.empiria.player.client.module.slideshow.slides;

import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import java.util.List;

public interface SlidesController {
	void setPresenter(Presenter presenter);

	void setSlides(List<SlideBean> slides);

	void playSlideshow();

	void stopSlideshow();

	void pauseSlideshow();

	void showPreviousSlide();

	void showNextSlide();

	interface Presenter {
		void setEnabledNextButton(boolean enabled);

		void setEnabledPreviousButton(boolean enabled);

		void setPlayButtonDown(boolean down);
	}
}
