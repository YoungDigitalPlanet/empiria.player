package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.List;

public class SlidesSwitcher {

	private List<SlideBean> slides;
	private int currSlideIndex;
	private final SlidePresenter presenter;

	@Inject
	public SlidesSwitcher(@ModuleScoped SlidePresenter presenter) {
		this.presenter = presenter;
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
		int nextSlideIndex = currSlideIndex + 1;
		return getSlideStartTime(nextSlideIndex);
	}

	private int getSlideStartTime(int index) {
		return slides.get(index).getStartTime();
	}

	public void showSlide(int indexToShow) {
		if (indexToShow < slides.size()) {
			currSlideIndex = indexToShow;
			showCurrentSlide();
		}
	}

	public int getCurrentSlideIndex() {
		return currSlideIndex;
	}

	private void showCurrentSlide() {
		if (currSlideIndex < slides.size()) {
			SlideBean currentSlide = slides.get(currSlideIndex);
			presenter.replaceViewData(currentSlide);
		}
	}
}
