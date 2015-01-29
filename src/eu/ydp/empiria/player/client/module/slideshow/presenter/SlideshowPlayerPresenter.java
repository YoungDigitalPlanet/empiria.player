package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.*;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowBean;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;

public class SlideshowPlayerPresenter {

	private final SlideshowPlayerView view;
	private final InlineBodyGeneratorSocketWrapper inlineBodyGeneratorWrapper;

	@Inject
	public SlideshowPlayerPresenter(SlideshowPlayerView view, InlineBodyGeneratorSocketWrapper inlineBodyGeneratorWrapper) {
		this.view = view;
		this.inlineBodyGeneratorWrapper = inlineBodyGeneratorWrapper;
	}

	public void init(SlideshowBean bean) {
		Element title = bean.getTitle().getTitleValue().getValue();
		InlineBodyGeneratorSocket inlineBodyGenerator = inlineBodyGeneratorWrapper.getInlineBodyGeneratorSocket();
		Widget titleView = inlineBodyGenerator.generateInlineBody(title);
		view.setTitle(titleView);
	}

	public void setPager(Widget pagerWidget) {
		view.addPager(pagerWidget);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
