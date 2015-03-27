package eu.ydp.empiria.player.client.module.feedback.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ImageFeedbackPresenter extends Composite implements ImageFeedback {

	private static ImageFeedbackViewUiBinder uiBinder = GWT.create(ImageFeedbackViewUiBinder.class);

	@UiTemplate("ImageFeedbackView.ui.xml")
	interface ImageFeedbackViewUiBinder extends UiBinder<Widget, ImageFeedbackPresenter> {
	}

	public ImageFeedbackPresenter() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	FlowPanel feedbackImageModule;

	@UiField
	Image feedbackImage;

	@Override
	public void setUrl(String url) {
		feedbackImage.setUrl(url);
	}

	@Override
	public String getUrl() {
		return feedbackImage.getUrl();
	}

	@Override
	public void show() {
		this.setVisible(true);
	}

	@Override
	public void hide() {
		this.setVisible(false);
	}

}
