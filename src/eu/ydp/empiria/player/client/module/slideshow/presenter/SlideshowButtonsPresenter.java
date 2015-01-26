package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlideshowButtonsPresenter {

	private final SlideshowButtonsView buttonsView;
	private SlideshowController controller;

	@Inject
	public SlideshowButtonsPresenter(@ModuleScoped SlideshowButtonsView buttonsView) {
		this.buttonsView = buttonsView;
		buttonsView.setPresenter(this);
	}

	public void setSlideshowController(SlideshowController controller) {
		this.controller = controller;
	}

	public void setEnabledNextButton(boolean enabled) {
		buttonsView.setEnabledNextButton(enabled);
	}

	public void setEnabledPreviousButton(boolean enabled) {
		buttonsView.setEnabledPreviousButton(enabled);
	}

	public void setPlayButtonDown(boolean isDown) {
		buttonsView.setPlayButtonDown(isDown);
	}

	public void onNextClick() {
		controller.showNextSlide();
	}

	public void onPreviousClick() {
		controller.showPreviousSlide();
	}

	public void onPlayClick() {
		if (buttonsView.isPlayButtonDown()) {
			controller.playSlideshow();
		} else {
			controller.pauseSlideshow();
		}
	}

	public void onStopClick() {
		buttonsView.setPlayButtonDown(false);
		controller.stopSlideshow();
	}
}
