package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowButtonsPresenter;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.List;

public class SlideshowController implements SlideshowSlidesController {

	private final SlidesSwitcher slidesSwitcher;
	private final SlideshowTimer timer;
	private final SlidesSorter slidesSorter;
	private final SlideshowButtonsPresenter buttonsPresenter;

	@Inject
	public SlideshowController(@ModuleScoped SlidesSwitcher slidesSwitcher, @ModuleScoped SlideshowButtonsPresenter buttonsPresenter, SlideshowTimer timer,
			SlidesSorter slidesSorter) {
		this.slidesSwitcher = slidesSwitcher;
		this.buttonsPresenter = buttonsPresenter;
		this.timer = timer;
		this.slidesSorter = slidesSorter;
	}

	public void init(List<SlideBean> slides) {
		initTimer();
		buttonsPresenter.setSlideshowController(this);
		slidesSorter.sortByTime(slides);
		slidesSwitcher.setSlides(slides);
		resetAndSetButtons();
	}

	@Override
	public void stopSlideshow() {
		pauseSlideshow();
		resetAndSetButtons();
	}

	@Override
	public void playSlideshow() {
		if (!slidesSwitcher.canSwitchToNextSlide()) {
			resetAndSetButtons();
		}

		showNextWithDelay();
	}

	@Override
	public void pauseSlideshow() {
		timer.cancel();
	}

	@Override
	public void showPreviousSlide() {
		slidesSwitcher.showPreviousSlide();
		setButtons();
	}

	@Override
	public void showNextSlide() {
		slidesSwitcher.showNextSlide();
		setButtons();
	}

	private void setButtons() {
		buttonsPresenter.setEnabledNextButton(slidesSwitcher.canSwitchToNextSlide());
		buttonsPresenter.setEnabledPreviousButton(slidesSwitcher.canSwitchToPreviousSlide());
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
		setButtons();
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