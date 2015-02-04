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
	private final SlideshowTimer timer;
	private final SlidesSorter slidesSorter;
	private final SlideshowButtonsPresenter buttonsPresenter;
	private final SlideshowPagerPresenter pagerPresenter;

	@Inject
	public SlideshowController(@ModuleScoped SlidesSwitcher slidesSwitcher, @ModuleScoped SlideshowButtonsPresenter buttonsPresenter,
			SlideshowPagerPresenter pagerPresenter, SlideshowTimer timer, SlidesSorter slidesSorter) {
		this.slidesSwitcher = slidesSwitcher;
		this.buttonsPresenter = buttonsPresenter;
		this.pagerPresenter = pagerPresenter;
		this.timer = timer;
		this.slidesSorter = slidesSorter;
	}

	public void init(List<SlideBean> slides, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		initTimer();
		buttonsPresenter.setSlideshowController(this);
		slidesSorter.sortByTime(slides);
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

		showNextWithDelay();
	}

	public void pauseSlideshow() {
		timer.cancel();
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

	private void showNextWithDelay() {
		int nextSlideTime = slidesSwitcher.getNextSlideStartTime();
		int currentSlideTime = slidesSwitcher.getCurrentSlideStartTime();

		int delay = nextSlideTime - currentSlideTime;
		timer.schedule(delay);
	}

	private void showAndSheduleNextSlide() {
		showNextSlide();
		if (slidesSwitcher.canSwitchToNextSlide()) {
			showNextWithDelay();
		} else {
			pauseSlideshow();
			buttonsPresenter.setPlayButtonDown(false);
		}
	}

	private void resetAndSetButtons() {
		slidesSwitcher.reset();
		updateButtons();
	}

	private void initTimer() {
		Command command = new Command() {

			@Override
			public void execute() {
				showAndSheduleNextSlide();
			}
		};
		timer.setCommand(command);
	}
}