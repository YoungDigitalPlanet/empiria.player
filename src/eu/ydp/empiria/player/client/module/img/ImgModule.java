package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class ImgModule implements ISimpleModule {

	protected Image img;
	
	public ImgModule(){
		img = new Image();
		img.setStyleName("qp-img");
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		String src = element.getAttribute("src");
		if (src != null)
			img.setUrl(src);
		String alt = element.getAttribute("alt");
		if (alt != null)
			img.setAltText(alt);
		String cls = element.getAttribute("class");
		if (cls != null)
			img.addStyleName(cls);
		String id = element.getAttribute("id");
		if (id != null  &&  !"".equals(id)  &&  getView() != null){
			getView().getElement().setId(id);
		}
	}
	
	@Override
	public Widget getView() {
		return img;
	}

}
