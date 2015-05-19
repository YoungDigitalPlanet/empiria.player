package eu.ydp.empiria.player.client.module.img.picture.player.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import eu.ydp.empiria.player.client.ConsoleLog;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class PicturePlayerViewImpl extends Composite implements PicturePlayerView {

	private static DefaultImgContentUiBinder uiBinder = GWT.create(DefaultImgContentUiBinder.class);

	@UiTemplate(value = "PicturePlayerView.ui.xml") interface DefaultImgContentUiBinder extends UiBinder<Widget, PicturePlayerViewImpl> {
	}

	@UiField
	protected Image image;
	@UiField
	protected FlowPanel container;
	@UiField
	protected CustomPushButton fullScreenButton;

	public PicturePlayerViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setClickHandler(ClickHandler handler) {
		fullScreenButton.addClickHandler(handler);
	}

	@Override
	public void setImage(String title, String url) {
		ConsoleLog.alert(title + ", " + url);
		image.setAltText(title);
		image.setUrl(url);
	}

}
