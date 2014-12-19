package eu.ydp.empiria.player.client.module.slideshow.view.slide;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import eu.ydp.empiria.player.client.module.slideshow.view.text.TextView;

public class SlideViewImpl extends Composite implements SlideView {

	@UiTemplate("SlideView.ui.xml")
	interface SlideViewUiBinder extends UiBinder<Widget, SlideViewImpl> {
	};

	private static SlideViewUiBinder slideWidgetBinder = GWT.create(SlideViewUiBinder.class);

	@UiField
	public Image image;

	@UiField
	public FlowPanel titlePanel;

	@UiField
	public FlowPanel narrationPanel;

	public SlideViewImpl() {
		initWidget(slideWidgetBinder.createAndBindUi(this));
	}

	@Override
	public void setSlideTitle(String title) {
		titlePanel.add(new TextView(title));
	}

	@Override
	public void clearSlideTitle() {
		titlePanel.clear();
	}

	@Override
	public void setNarration(String narration) {
		narrationPanel.add(new TextView(narration));
	}

	@Override
	public void clearNarration() {
		narrationPanel.clear();
	}

	@Override
	public void setImage(String src) {
		image.setUrl(src);
	}
}
