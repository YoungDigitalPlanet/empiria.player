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

	public int getCurrentSlide() {
		return currSlideIndex;
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
		return (currSlideIndex + 1 < slides.size());
	}

	public boolean canSwitchToPreviousSlide() {
		return (currSlideIndex > 0);
	}

	public int getCurrentSlideStartTime() {
		return getSlideStartTime(currSlideIndex);
	}

	public int getNextSlideStartTime() {
		return getSlideStartTime(currSlideIndex + 1);
	}

	private int getSlideStartTime(int index) {
		if (index < slides.size()) {
			return slides.get(index).getStartTime();
		} else {
			return 0;
		}
	}

	private void showCurrentSlide() {
		if (currSlideIndex < slides.size()) {
			slidePresenter.replaceView(slides.get(currSlideIndex));
		}
	}
}
