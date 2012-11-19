package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.user.client.ui.Widget;

public interface DraggableObject<W extends Widget> extends HasDragHandlers {
	public W getDraggableWidget();
	public void setDisableDrag(boolean disable);
}
