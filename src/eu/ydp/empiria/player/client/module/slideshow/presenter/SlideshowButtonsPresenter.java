package eu.ydp.empiria.player.client.module.slideshow.presenter;

import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowSlidesController;

public interface SlideshowButtonsPresenter {
	void setEnabledNextButton(boolean enabled);

	void setEnabledPreviousButton(boolean enabled);

	void setPlayButtonDown(boolean isDown);

	void setSlideshowController(SlideshowSlidesController controller);

	void onNextClick();

	void onPreviousClick();

	void onPlayClick();

	void onStopClick();
}
