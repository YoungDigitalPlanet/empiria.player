package eu.ydp.empiria.player.client.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class ElementWrapperWidget extends Widget {
	public ElementWrapperWidget(Element e){
		setElement(e);
	}
}
