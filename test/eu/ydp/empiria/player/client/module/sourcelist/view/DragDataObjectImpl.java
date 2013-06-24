package eu.ydp.empiria.player.client.module.sourcelist.view;

import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;

public class DragDataObjectImpl implements DragDataObject {

	private String itemID;
	@Override
	public String toJSON() {
		return null;
	}
	@Override
	public void setItemId(String itemID) {
		this.itemID = itemID;
	}

	@Override
	public String getItemId() {
		return itemID;
	}

}
