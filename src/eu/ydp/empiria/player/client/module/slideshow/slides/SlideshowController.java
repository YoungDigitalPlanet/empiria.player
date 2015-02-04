package eu.ydp.empiria.player.client.module.slideshow.slides;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.presenter.*;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlideshowController {

	private final SlidesSwitcher slidesSwitcher;
	private final SlideshowButtonsPresenter buttonsPresenter;
	private final SlideshowPagerPresenter pagerPresenter;

	@Inject
	public SlideshowController(@ModuleScoped SlidesSwitcher slidesSwitcher, @ModuleScoped SlideshowButtonsPresenter buttonsPresenter,
			SlideshowPagerPresenter pagerPresenter) {
		this.slidesSwitcher = slidesSwitcher;
		this.buttonsPresenter = buttonsPresenter;
		this.pagerPresenter = pagerPresenter;
	}

	public void init(List<SlideBean> slides, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		buttonsPresenter.setSlideshowController(this);
		slidesSwitcher.init(slides, inlineBodyGeneratorSocket);
		resetAndSetButtons();
	}

	public Widget initPager(int slidesSize) {
		pagerPresenter.setSlideshowController(this);
		return pagerPresenter.createPager(slidesSize);
	}

	public void showSlide(int indexToShow) {
		slidesSwitcher.showSlide(indexToShow);
		updateButtons();
	}

	public void stopSlideshow() {
		pauseSlideshow();
		resetAndSetButtons();
	}

	public void playSlideshow() {
		if (!slidesSwitcher.canSwitchToNextSlide()) {
			resetAndSetButtons();
		}
		buttonsPresenter.setPlayButtonDown(true);
	}

	public void pauseSlideshow() {
		buttonsPresenter.setPlayButtonDown(false);
	}

	public void showPreviousSlide() {
		slidesSwitcher.showPreviousSlide();
		updateButtons();
	}

	public void showNextSlide() {
		slidesSwitcher.showNextSlide();
		updateButtons();
	}

	private void updateButtons() {
		buttonsPresenter.setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		buttonsPresenter.setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
		pagerPresenter.updateButtons(slidesSwitcher.getCurrentSlideIndex());
	}

	private void resetAndSetButtons() {
		slidesSwitcher.reset();
		updateButtons();
	}
}