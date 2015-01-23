package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.common.collect.Lists;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.*;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.SlideshowPagerView;
import eu.ydp.gwtutil.client.event.factory.Command;
import java.util.List;

public class SlideshowPagerPresenter {

	@Inject
	private Provider<SlideshowPagerButtonPresenter> pagerButtonProvider;
	@Inject
	private SlideshowPagerView view;
	private SlideshowController slideshowController;

	private final List<SlideshowPagerButtonPresenter> pagerButtonsList = Lists.newArrayList();

	public Widget createPager(int slidesSize) {
		for (int i = 0; i < slidesSize; i++) {
			SlideshowPagerButtonPresenter pagerButton = createPagerButton();
			pagerButtonsList.add(pagerButton);
			view.addPager(pagerButton.getView());
		}

		return view.asWidget();
	}

	public void setSlideshowController(SlideshowController slideshowController) {
		this.slideshowController = slideshowController;
	}

	public void updateButtons(int currentSlideIndex) {
		deactivateAllPagerButtons();
		activatePagerButton(currentSlideIndex);
	}

	private SlideshowPagerButtonPresenter createPagerButton() {
		SlideshowPagerButtonPresenter pagerButton = pagerButtonProvider.get();
		Command clickCommand = createClickCommand(pagerButton);
		pagerButton.setClickCommand(clickCommand);
		return pagerButton;
	}

	private Command createClickCommand(final SlideshowPagerButtonPresenter pagerButton) {
		final int indexToShow = pagerButtonsList.size();

		return new Command() {

			@Override
			public void execute(NativeEvent event) {
				deactivateAllPagerButtons();
				pagerButton.activatePagerButton();
				slideshowController.showSlide(indexToShow);
			}
		};
	}

	private void deactivateAllPagerButtons() {
		for (SlideshowPagerButtonPresenter pagerButton : pagerButtonsList) {
			pagerButton.deactivatePagerButton();
		}
	}

	private void activatePagerButton(int currentSlideIndex) {
		SlideshowPagerButtonPresenter button = pagerButtonsList.get(currentSlideIndex);
		button.activatePagerButton();
	}
}
