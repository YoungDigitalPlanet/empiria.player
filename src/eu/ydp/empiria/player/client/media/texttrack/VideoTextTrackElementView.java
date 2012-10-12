package eu.ydp.empiria.player.client.media.texttrack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VideoTextTrackElementView extends Composite implements VideoTextTrackElementPresenter {
	private static VideoTextTrackElementViewUiBinder uiBinder = GWT.create(VideoTextTrackElementViewUiBinder.class);

	interface VideoTextTrackElementViewUiBinder extends UiBinder<Widget, VideoTextTrackElementView> {
	}

	@UiField
	protected FlowPanel text;

	public VideoTextTrackElementView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setInnerText(String text) {
		this.text.getElement().setInnerText(text);
	}
}
