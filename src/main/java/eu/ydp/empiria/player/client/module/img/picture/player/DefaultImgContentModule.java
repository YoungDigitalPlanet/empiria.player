package eu.ydp.empiria.player.client.module.img.picture.player;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.ConsoleLog;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.*;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class DefaultImgContentModule implements ImgContent {

	private PicturePlayerStructure picturePlayerStructure;
	private IJSONService ijsonService;
	private DefaultImgContentPresenter presenter;

	@Inject
	public DefaultImgContentModule(PicturePlayerStructure picturePlayerStructure, IJSONService ijsonService, DefaultImgContentPresenter presenter) {
		this.picturePlayerStructure = picturePlayerStructure;
		this.ijsonService = ijsonService;
		this.presenter = presenter;
	}

	private boolean template = false;

	@Override
	public Widget asWidget() {
		return presenter.getView();
	}

	@Override
	public void init(Element element, ModuleSocket moduleSocket) {
		picturePlayerStructure.createFromXml(element.toString(), ijsonService.createArray());
		PicturePlayerBean bean = picturePlayerStructure.getBean();

		ConsoleLog.alert(bean.getFullscreenMode());
		ConsoleLog.alert(bean.getSrc());
		ConsoleLog.alert(bean.getSrcFullScreen());

		if (bean.getImgTitleBean() != null) {
			ConsoleLog.alert(bean.getImgTitleBean().getTitleName().getValue().toString());
		}

		Element titleNodes = XMLUtils.getFirstElementWithTagName(element, "title");
		String title = XMLUtils.getTextFromChilds(titleNodes);

		//		view.setImage(title, element.getAttribute("src"));
		//		initFullScreenMediaButton(element);
	}

	public void setTemplate(boolean template) {
		this.template = template;
	}

}
