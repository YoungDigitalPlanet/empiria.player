package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.List;

public class SlidesSwitcher {

	private List<SlideBean> slides;
	private int currSlideIndex;
	private final SlidePresenter slidePresenter;

	@Inject
	public SlidesSwitcher(@ModuleScoped SlidePresenter slidePresenter) {
		this.slidePresenter = slidePresenter;
	}

	public void setSlides(List<SlideBean> slides) {
		this.slides = slides;
	}

	public void showNextSlide() {
		if (canSwitchToNextSlide()) {
			currSlideIndex++;
		}

		showCurrentSlide();
	}

	public void showPreviousSlide() {
		if (canSwitchToPreviousSlide()) {
			currSlideIndex--;
		}

		showCurrentSlide();
	}

	public void reset() {
		currSlideIndex = 0;
		showCurrentSlide();
	}

	public boolean canSwitchToNextSlide() {
		int nextSlide = currSlideIndex + 1;
		return nextSlide < slides.size();
	}

	public boolean canSwitchToPreviousSlide() {
		return currSlideIndex > 0;
	}

	public int getCurrentSlideStartTime() {
		return getSlideStartTime(currSlideIndex);
	}

	public int getNextSlideStartTime() {
		int nextSlide = currSlideIndex + 1;
		return getSlideStartTime(nextSlide);
	}

	private int getSlideStartTime(int index) {
		return slides.get(index).getStartTime();
	}

	private void showCurrentSlide() {
		if (currSlideIndex < slides.size()) {
			SlideBean currentSlide = slides.get(currSlideIndex);
			slidePresenter.replaceViewData(currentSlide);
		}
	}
}
