package eu.ydp.empiria.player.client.module.slideshow.view.slide;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class SlideViewImpl extends Composite implements SlideView {

	@UiTemplate("SlideView.ui.xml")
	interface SlideViewUiBinder extends UiBinder<Widget, SlideViewImpl> {
	};

	private static SlideViewUiBinder slideWidgetBinder = GWT.create(SlideViewUiBinder.class);

	@UiField
	public Image image;

	@UiField
	public InlineLabel titleText;

	@UiField
	public InlineLabel narrationText;

	public SlideViewImpl() {
		initWidget(slideWidgetBinder.createAndBindUi(this));
	}

	@Override
	public void setSlideTitle(String title) {
		titleText.setText(title);
	}

	@Override
	public void clearSlideTitle() {
		titleText.setText("");
	}

	@Override
	public void setNarration(String narration) {
		narrationText.setText(narration);
	}

	@Override
	public void clearNarration() {
		narrationText.setText("");
	}

	@Override
	public void setImage(String src) {
		image.setUrl(src);
	}
}
