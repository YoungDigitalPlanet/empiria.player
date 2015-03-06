package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.ConsoleLog;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.SlideEndHandler;
import eu.ydp.empiria.player.client.module.slideshow.presenter.*;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideshowSoundController;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.List;

public class SlideshowController implements PlayerEventHandler {

	private final SlidesSwitcher slidesSwitcher;
	private final SlideshowButtonsPresenter buttonsPresenter;
	private final SlideshowPagerPresenter pagerPresenter;
	private final SlideshowSoundController slideshowSoundController;
	private final EventsBus eventsBus;

	@Inject
	public SlideshowController(@ModuleScoped SlidesSwitcher slidesSwitcher, @ModuleScoped SlideshowButtonsPresenter buttonsPresenter,
			SlideshowPagerPresenter pagerPresenter, @ModuleScoped SlideshowSoundController slideshowSoundController, EventsBus eventsBus) {
		this.slidesSwitcher = slidesSwitcher;
		this.buttonsPresenter = buttonsPresenter;
		this.pagerPresenter = pagerPresenter;
		this.slideshowSoundController = slideshowSoundController;
		this.eventsBus = eventsBus;
	}

	private final SlideEndHandler slideEnd = new SlideEndHandler() {

		@Override
		public void onEnd() {
			continuePlaySlideshow();
		}
	};

	public void init(List<SlideBean> slides, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		buttonsPresenter.setSlideshowController(this);
		slidesSwitcher.init(slides, inlineBodyGeneratorSocket);
		slidesSwitcher.setSlideEnd(slideEnd);
		initSounds(slides);
		resetAndSetButtons();

		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGING), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.SLIDESHOW_STARTED), this);
	}

	private void initSounds(List<SlideBean> slides) {
		for (SlideBean slide : slides) {
			if (slide.hasSound()) {
				String audiopath = slide.getSound().getSrc();
				slideshowSoundController.initSound(audiopath);
			}
		}
	}

	public Widget initPager(int slidesSize) {
		pagerPresenter.setSlideshowController(this);
		return pagerPresenter.createPager(slidesSize);
	}

	public void showSlide(int indexToShow) {
		slidesSwitcher.showSlide(indexToShow);
		slidesSwitcher.stopAndPlaySlide();
		updateButtons();
		buttonsPresenter.setPlayButtonDown(true);
	}

	public void stopSlideshow() {
		resetAndSetButtons();
	}

	public void playSlideshow() {
		slidesSwitcher.playSlide();
		fireEventFromSlideshow();
	}

	private void fireEventFromSlideshow() {
		PlayerEvent slideshowStartedEvent = new PlayerEvent(PlayerEventTypes.SLIDESHOW_STARTED, this, this);
		eventsBus.fireEvent(slideshowStartedEvent);
	}

	public void pauseSlideshow() {
		slidesSwitcher.pauseSlide();
		buttonsPresenter.setPlayButtonDown(false);
	}

	public void showPreviousSlide() {
		slidesSwitcher.showPreviousSlide();
		slidesSwitcher.stopAndPlaySlide();
		updateButtons();
		buttonsPresenter.setPlayButtonDown(true);
	}

	public void showNextSlide() {
		slidesSwitcher.showNextSlide();
		slidesSwitcher.stopAndPlaySlide();
		updateButtons();
		buttonsPresenter.setPlayButtonDown(true);
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
	}

	private void resetAndSetButtons() {
		slidesSwitcher.reset();
		updateButtons();
		buttonsPresenter.setPlayButtonDown(false);
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if (isEventNotFromThisSlideshow(event)) {
			pauseSlideshow();
		}
	}

	private boolean isEventNotFromThisSlideshow(PlayerEvent event) {
		return !event.getSource().equals(this);
	}
}