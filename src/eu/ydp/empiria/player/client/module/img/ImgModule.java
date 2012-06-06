package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

/**
 * Klasa odpowiedzialna za renderwoanie elementu img.
 *
 */
public class ImgModule extends SimpleModuleBase implements Factory<ImgModule> {

	protected ImgContent content;
	
	protected ImgModuleView view; 

	public ImgModule() {
		view = new ImgModuleView();
	}


	@Override
	protected void initModule(Element element) {

		if ("labelled".equals(element.getAttribute("mode"))  ||  element.getElementsByTagName("label").getLength() > 0){
			content = new LabelledImgContent();
		} else if ("explorable".equals(element.getAttribute("mode"))){
			content = new ExplorableImgContent();
		} else {
			content = new DefaultImgContent();
		}
		
		content.init(element, getModuleSocket());
		
		view.setContent(content);

		NodeList titleNodes = element.getElementsByTagName("title");
		if (titleNodes.getLength() > 0) {
			Widget titleWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(titleNodes.item(0));
			if (titleWidget != null) {
				view.setTitle(titleWidget);
			}
		}
		NodeList descriptionNodes = element.getElementsByTagName("description");
		if (descriptionNodes.getLength() > 0) {
			Widget descriptionWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(descriptionNodes.item(0));
			if (descriptionWidget != null) {
				view.setDescription(descriptionWidget);
			}
		}
	}


	@Override
	public Widget getView() {
		return view;
	}

	@Override
	public ImgModule getNewInstance() {
		return new ImgModule();
	}



}
