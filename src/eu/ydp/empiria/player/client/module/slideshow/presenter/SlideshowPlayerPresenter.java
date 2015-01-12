package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowBean;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;

public class SlideshowPlayerPresenter {

	private final SlideshowPlayerView view;

	@Inject
	public SlideshowPlayerPresenter(SlideshowPlayerView view) {
		this.view = view;
	}

	public void init(SlideshowBean bean) {
		String title = bean.getTitle();
		view.setTitle(title);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
