package eu.ydp.empiria.player.client.module.slideshow.view.player;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlideshowPlayerViewImpl implements SlideshowPlayerView {

	@UiTemplate("SlideshowPlayerView.ui.xml")
	interface SlideshowModuleUiBinder extends UiBinder<Widget, SlideshowPlayerViewImpl> {
	};

	private final SlideshowModuleUiBinder uiBinder = GWT.create(SlideshowModuleUiBinder.class);

	@UiField
	protected FlowPanel titlePanel;

	@UiField
	protected Panel mainPanel;

	@UiField
	protected Panel slidesPanel;

	@UiField
	protected Panel pagerPanel;

	@Inject
	public SlideshowPlayerViewImpl(@ModuleScoped SlideView slideView, @ModuleScoped SlideshowButtonsView buttonsView) {
		uiBinder.createAndBindUi(this);
		slidesPanel.add(slideView);
		mainPanel.add(buttonsView.asWidget());
	}

	@Override
	public void setTitle(Widget title) {
		titlePanel.clear();
		titlePanel.add(title);
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	@Override
	public void addPager(Widget pager) {
		pagerPanel.add(pager);
	}
}
