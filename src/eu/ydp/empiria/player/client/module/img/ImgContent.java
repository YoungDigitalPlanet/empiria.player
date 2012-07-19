package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.ModuleSocket;

public interface ImgContent extends IsWidget {

	public void init(Element element, ModuleSocket moduleSocket);
	
}
