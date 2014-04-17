package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.PushButton;

public class PushButtonWithIndex extends PushButton {

	public PushButtonWithIndex(String text) {
		super(text);
		this.getElement().getElementsByTagName("input").getItem(0).getStyle().setPosition(Position.RELATIVE);
	}

	protected int index = -1;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
