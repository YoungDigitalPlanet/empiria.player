package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.media.button.PicturePlayerFullScreenMediaButton;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class DefaultImgContent extends Composite implements ImgContent {

	private static DefaultImgContentUiBinder uiBinder = GWT.create(DefaultImgContentUiBinder.class);

	interface DefaultImgContentUiBinder extends UiBinder<Widget, DefaultImgContent> {
	}

	@UiField
	protected Image image;
	@UiField
	protected FlowPanel container;

	/**
	 * czy element renderowany jest w kontekscie szablonu
	 */
	private boolean template = false;

	@Inject
	private PicturePlayerFullScreenMediaButton fullScreenMediaButton;

	public DefaultImgContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setTemplate(boolean template) {
		this.template = template;
	}

	@Override
	public void init(Element element, ModuleSocket moduleSocket) {
		Element titleNodes = XMLUtils.getFirstElementWithTagName(element, "title");
		final String title = XMLUtils.getTextFromChilds(titleNodes);

		image.setAltText(title);
		image.setUrl(element.getAttribute("src"));

		initFullScreenMediaButton(element);
	}


	private void initFullScreenMediaButton(Element element) {
		if(PicturePlayerFullScreenMediaButton.isSupported(element) && !template) {
			fullScreenMediaButton.init(element);
			container.add(fullScreenMediaButton);
		}
	}
}
