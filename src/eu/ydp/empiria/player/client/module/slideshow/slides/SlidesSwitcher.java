package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
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
	private Command showNextSlideCommand;

	@Inject
	public SlidesSwitcher(@ModuleScoped SlidePresenter presenter, SlideSoundController slideSoundController) {
		this.slideSoundController = slideSoundController;
		this.presenter = presenter;

	}

	private final Command endSlideCommand = new Command() {

		@Override
		public void execute() {
			showNextSlideCommand.execute();
		}
	};

	public void setSlides(List<SlideBean> slides) {
		this.slides = slides;
	}

	public void initSounds() {
		List<AudioBean> sounds = Lists.newArrayList();

		for (SlideBean slide : slides) {
			if (slide.hasAudio()) {
				sounds.add(slide.getAudio());
			}
		}

		slideSoundController.initSounds(sounds);
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

	public void stopSlide() {
		if (hasCurrentSlideAudio()) {
			String audiopath = getCurrentAudioSource();
			slideSoundController.stopSound(audiopath);
		}
	}

	public void stopAndPlaySlide() {
		slideSoundController.stopAllSounds();
		playSlide();
	}

	public void playSlide() {
		if (hasCurrentSlideAudio()) {
			String audiopath = getCurrentAudioSource();
			slideSoundController.playSound(audiopath, endSlideCommand);
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
		currSlideIndex = 0;
		showCurrentSlide();
	}

	private void showCurrentSlide() {
		if (currSlideIndex < slides.size()) {
			SlideBean currentSlide = slides.get(currSlideIndex);
			presenter.replaceViewData(currentSlide);
		}
	}

	public void setShowNextSlideCommand(Command showNextSlideCommand) {
		this.showNextSlideCommand = showNextSlideCommand;
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