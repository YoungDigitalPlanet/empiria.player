package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.SlideEnd;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideSoundController;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.List;

public class SlidesSwitcher {

	private List<SlideBean> slides;
	private int currSlideIndex;
	private final SlidePresenter presenter;
	private final SlideSoundController slideSoundController;
	private SlideEnd slideEnd;

	@Inject
	public SlidesSwitcher(@ModuleScoped SlidePresenter presenter, SlideSoundController slideSoundController) {
		this.slideSoundController = slideSoundController;
		this.presenter = presenter;
	}

	public void init(List<SlideBean> slides, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		this.slides = slides;
		presenter.setInlineBodyGenerator(inlineBodyGeneratorSocket);
		initSounds();
	}

	private void initSounds() {
		for (SlideBean slide : slides) {
			if (slide.hasAudio()) {
				String audiopath = slide.getAudio().getSrc();
				slideSoundController.initSound(audiopath);
			}
		}
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

	public boolean canSwitchToNextSlide() {
		int nextSlide = currSlideIndex + 1;
		return nextSlide < slides.size();
	}

	public boolean canSwitchToPreviousSlide() {
		return currSlideIndex > 0;
	}

	public void pauseSlide() {
		if (hasCurrentSlideAudio()) {
			String audiopath = getCurrentAudioSource();
			slideSoundController.pauseSound(audiopath);
		}
	}

	public void stopAndPlaySlide() {
		slideSoundController.stopAllSounds();
		playSlide();
	}

	public void playSlide() {
		if (hasCurrentSlideAudio()) {
			String audiopath = getCurrentAudioSource();
			slideSoundController.playSound(audiopath, slideEnd);
		}
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

	public void reset() {
		slideSoundController.stopAllSounds();
		currSlideIndex = 0;
		showCurrentSlide();
	}

	private void showCurrentSlide() {
		if (currSlideIndex < slides.size()) {
			SlideBean currentSlide = slides.get(currSlideIndex);
			presenter.replaceViewData(currentSlide);
		}
	}

	public void setSlideEnd(SlideEnd slideEnd) {
		this.slideEnd = slideEnd;
	}

	private boolean hasCurrentSlideAudio() {
		SlideBean currentSlide = slides.get(currSlideIndex);
		return currentSlide.hasAudio();
	}

	private String getCurrentAudioSource() {
		SlideBean currentSlide = slides.get(currSlideIndex);
		AudioBean currentSlideAudio = currentSlide.getAudio();
		return currentSlideAudio.getSrc();
	}
}