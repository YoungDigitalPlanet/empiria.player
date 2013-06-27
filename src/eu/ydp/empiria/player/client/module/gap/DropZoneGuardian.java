package eu.ydp.empiria.player.client.module.gap;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public class DropZoneGuardian {

	private final DroppableObject<TextBox> droppable;	
	protected Widget moduleWidget;
	private StyleNameConstants styleNames;
	
	public DropZoneGuardian(DroppableObject<TextBox> droppable, Widget moduleWidget, StyleNameConstants styleNameConstants) {
		this.droppable = droppable;
		this.moduleWidget  = moduleWidget;
		this.styleNames = styleNameConstants;
	}
	
	public void lockDropZone() {
		droppable.setDisableDrop(true);
		moduleWidget.addStyleName(styleNames.QP_DROP_ZONE_LOCKED());
	}

	public void unlockDropZone() {
		droppable.setDisableDrop(false);
		moduleWidget.removeStyleName(styleNames.QP_DROP_ZONE_LOCKED());
	}
}
