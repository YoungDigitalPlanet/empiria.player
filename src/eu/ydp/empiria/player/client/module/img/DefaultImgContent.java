package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.media.button.PicturePlayerFullScreenMediaButon;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class DefaultImgContent extends Composite implements ImgContent {

	private static DefaultImgContentUiBinder uiBinder = GWT.create(DefaultImgContentUiBinder.class);

	interface DefaultImgContentUiBinder extends UiBinder<Widget, DefaultImgContent> {
	}

	@UiField
	protected Image image;

	@UiField
	protected FlowPanel container;

	@Inject
	private PicturePlayerFullScreenMediaButon fullScreenMediaButon;

	public DefaultImgContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void init(Element element, ModuleSocket moduleSocket) {
		Element titleNodes = XMLUtils.getFirstElementWithTagName(element, "title");
		final String title = XMLUtils.getTextFromChilds(titleNodes);
		final String srcFullScreen = element.getAttribute("srcFullScreen");
		image.setAltText(title);
		image.setUrl(element.getAttribute("src"));
		if (srcFullScreen != null && !srcFullScreen.trim().isEmpty()) {
			fullScreenMediaButon.addImage(srcFullScreen, title);
			fullScreenMediaButon.init();
			container.add(fullScreenMediaButon);
		}
	}

}
