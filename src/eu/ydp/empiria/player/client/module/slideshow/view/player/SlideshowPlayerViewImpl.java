package eu.ydp.empiria.player.client.module.slideshow.view.player;

import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;

import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.text.TextView;
import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlideshowPlayerViewImpl implements SlideshowPlayerView {

	@UiTemplate("SlideshowPlayerView.ui.xml")
	interface SlideshowModuleUiBinder extends UiBinder<Widget, SlideshowPlayerViewImpl> {
	};

	private final SlideshowModuleUiBinder uiBinder = GWT.create(SlideshowModuleUiBinder.class);

	@UiField
	protected Panel titlePanel;

	@UiField
	protected Panel mainPanel;

	@UiField
	protected Panel slidesPanel;

	@Inject
	public SlideshowPlayerViewImpl(@ModuleScoped SlideView slideView, @ModuleScoped SlideshowButtonsView buttonsView) {
		uiBinder.createAndBindUi(this);
		slidesPanel.add(slideView);
		addPanelWidget(buttonsView.asWidget());
	}

	@Override
	public void setTitle(String title) {
		titlePanel.add(new TextView(title));
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	@Override
	public void setViewClass(String className) {
		if (!Strings.isNullOrEmpty(className) && mainPanel != null) {
			mainPanel.addStyleName(className);
		}
	}

	private void addPanelWidget(Widget widget) {
		mainPanel.add(widget);
	}
}
