package eu.ydp.empiria.player.client.module.sourcelist.view;

import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;

public class DragDataObjectImpl implements DragDataObject {

	private String prev;
	private String value;

	@Override
	public String getPreviousValue() {
		return prev;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String toJSON() {
		return null;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setPreviousValue(String value) {
		this.prev = value;
	}

}
