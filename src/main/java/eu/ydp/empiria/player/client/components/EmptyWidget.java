package eu.ydp.empiria.player.client.components;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

public class EmptyWidget extends Widget {
	public EmptyWidget() {
		setElement(Document.get().createElement("span"));
	}

	@Override
	public com.google.gwt.user.client.Element getElement() {
		return null;
	}
}
