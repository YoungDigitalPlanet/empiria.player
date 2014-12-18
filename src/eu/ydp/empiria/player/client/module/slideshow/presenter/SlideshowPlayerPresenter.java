package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlidesController;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import java.util.List;

public class SlideshowPlayerPresenter implements IsWidget, SlidesController.Presenter, SlideshowButtonsView.Presenter {

	private final SlideshowPlayerView playerView;
	private final SlideshowButtonsView buttonsView;
	private final SlidesController slidesController;

	@Inject
	public SlideshowPlayerPresenter(@ModuleScoped SlideshowPlayerView playerView, @ModuleScoped SlideshowButtonsView buttonsView,
			@ModuleScoped SlidesController slidesController) {
		this.playerView = playerView;
		this.buttonsView = buttonsView;
		this.slidesController = slidesController;
		buttonsView.setPresenter(this);
		slidesController.setPresenter(this);
	}

	public void init(SlideshowBean bean) {
		String title = bean.getTitle();
		playerView.setTitle(title);

		List<SlideBean> slideBeans = bean.getSlideBeans();
		slidesController.setSlides(slideBeans);
	}

	@Override
	public Widget asWidget() {
		return playerView.asWidget();
	}

	@Override
	public void setEnabledNextButton(boolean enabled) {
		buttonsView.setEnabledNextButton(enabled);
	}

	@Override
	public void setEnabledPreviousButton(boolean enabled) {
		buttonsView.setEnabledPreviousButton(enabled);
	}

	@Override
	public void setPlayButtonDown(boolean isDown) {
		buttonsView.setPlayButtonDown(isDown);
	}

	@Override
	public void executeNext() {
		slidesController.showNextSlide();
	}

	@Override
	public void executePrevious() {
		slidesController.showPreviousSlide();
	}

	@Override
	public void executePlay() {
		slidesController.playSlideshow();
	}

	@Override
	public void executePause() {
		slidesController.pauseSlideshow();
	}

	@Override
	public void executeStop() {
		slidesController.stopSlideshow();
	}
}
