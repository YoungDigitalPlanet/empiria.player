package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.presenter.*;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.List;

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

	private final Command nextSlideCommand = new Command() {

		@Override
		public void execute() {
			boolean isPlaying = buttonsPresenter.isPlayButtonDown();
			if (isPlaying) {
				continuePlaySlideshow();
			}
		}
	};

	public void init(List<SlideBean> slides, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		buttonsPresenter.setSlideshowController(this);
		slidesSwitcher.init(slides, inlineBodyGeneratorSocket);
		slidesSwitcher.initSounds();
		slidesSwitcher.setShowNextSlideCommand(nextSlideCommand);
		resetAndSetButtons();
	}

	public Widget initPager(int slidesSize) {
		pagerPresenter.setSlideshowController(this);
		return pagerPresenter.createPager(slidesSize);
	}

	public void showSlide(int indexToShow) {
		slidesSwitcher.showSlide(indexToShow);
		slidesSwitcher.stopAndPlaySlide();
		updateButtons();
	}

	public void stopSlideshow() {
		slidesSwitcher.stopSlide();
		resetAndSetButtons();
	}

	public void playSlideshow() {
		slidesSwitcher.playSlide();
	}

	public void pauseSlideshow() {
		slidesSwitcher.pauseSlide();
	}

	public void showPreviousSlide() {
		slidesSwitcher.showPreviousSlide();
		slidesSwitcher.stopAndPlaySlide();
		updateButtons();
	}

	public void showNextSlide() {
		slidesSwitcher.showNextSlide();
		slidesSwitcher.stopAndPlaySlide();
		updateButtons();
	}

	private void continuePlaySlideshow() {
		if (slidesSwitcher.canSwitchToNextSlide()) {
			showNextSlide();
		} else {
			stopSlideshow();
		}
	}

	private void updateButtons() {
		buttonsPresenter.setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		buttonsPresenter.setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
		pagerPresenter.updateButtons(slidesSwitcher.getCurrentSlideIndex());
		buttonsPresenter.setPlayButtonDown(true);
	}

	private void resetAndSetButtons() {
		slidesSwitcher.reset();
		updateButtons();
		buttonsPresenter.setPlayButtonDown(false);
	}
}