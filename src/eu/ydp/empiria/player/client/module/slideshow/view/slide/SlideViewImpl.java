package eu.ydp.empiria.player.client.module.slideshow.view.slide;

import eu.ydp.empiria.player.client.module.slideshow.view.text.TextView;

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
	public FlowPanel titlePanel;

	@UiField
	public FlowPanel narrationPanel;

	public SlideViewImpl() {
		initWidget(slideWidgetBinder.createAndBindUi(this));
	}

	@Override
	public void setSlideTitle(String title) {
		setTextToPanel(titlePanel, title);
	}

	@Override
	public void setNarration(String narration) {
		setTextToPanel(narrationPanel, narration);
	}

	@Override
	public void setImage(String src) {
		if (src != null) {
			image.setUrl(src);
		}
	}

	private void setTextToPanel(FlowPanel element, String innerText) {
		element.clear();
		if (!innerText.isEmpty()) {
			element.add(new TextView(innerText));
		}
	}
}
