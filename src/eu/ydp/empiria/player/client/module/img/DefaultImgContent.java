package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.ModuleSocket;

public class DefaultImgContent extends Composite implements ImgContent {

	private static DefaultImgContentUiBinder uiBinder = GWT.create(DefaultImgContentUiBinder.class);

	interface DefaultImgContentUiBinder extends UiBinder<Widget, DefaultImgContent> {
	}

	@UiField
	Image image;
	
	public DefaultImgContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void init(Element element, ModuleSocket moduleSocket) {
		image.setUrl(element.getAttribute("src"));
	}
	

}
