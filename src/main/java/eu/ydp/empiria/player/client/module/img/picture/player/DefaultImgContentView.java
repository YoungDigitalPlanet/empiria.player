package eu.ydp.empiria.player.client.module.img.picture.player;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.media.button.PicturePlayerFullScreenMediaButton;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class DefaultImgContentView extends Composite {

	private static DefaultImgContentUiBinder uiBinder = GWT.create(DefaultImgContentUiBinder.class);

	interface DefaultImgContentUiBinder extends UiBinder<Widget, DefaultImgContentView> {
	}

	@UiField
	protected Image image;
	@UiField
	protected FlowPanel container;

	public DefaultImgContentView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setImage(String title, String url) {
		image.setAltText(title);
		image.setUrl(url);
	}

	public void addToContainer(Composite element) {
		container.add(element);
	}

}
