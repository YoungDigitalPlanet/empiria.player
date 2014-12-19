package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowSlidesController;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlideshowButtonsPresenter implements ButtonsPresenter, SlideshowButtonsView.Presenter {

	private final SlideshowButtonsView buttonsView;
	private SlideshowSlidesController controller;

	@Inject
	public SlideshowButtonsPresenter(@ModuleScoped SlideshowButtonsView buttonsView) {
		this.buttonsView = buttonsView;
		buttonsView.setPresenter(this);
	}

	@Override
	public void setSlideshowController(SlideshowSlidesController controller) {
		this.controller = controller;
	}

	@Override
	public void setEnabledNextButton(boolean enabled) {
		buttonsView.setEnabledNextButton(enabled);
	}

	@Override
	public void setEnabledPreviousButton(boolean enabled) {
		buttonsView.setEnabledPreviousButton(enabled);
	}

	@Override
	public void setPlayButtonDown(boolean isDown) {
		buttonsView.setPlayButtonDown(isDown);
	}

	@Override
	public void executeNext() {
		controller.showNextSlide();
	}

	@Override
	public void executePrevious() {
		controller.showPreviousSlide();
	}

	@Override
	public void executePlay() {
		controller.playSlideshow();
	}

	@Override
	public void executePause() {
		controller.pauseSlideshow();
	}

	@Override
	public void executeStop() {
		controller.stopSlideshow();
	}
}
