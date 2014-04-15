package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class TouchableExtendableListItem extends FlowPanel {

	public TouchableExtendableListItem(String itemName) {
		Label nameLabel = new Label(itemName);
		clear();
		add(nameLabel);
		setStyleName("tel-item");
		init(this.getElement());
	}

	public native void init(Element el)/*-{
										el.ondrag = function () { return false; };
										el.onselectstart = function () { return false; };
										el.style.MozUserSelect="none"
										}-*/;

}