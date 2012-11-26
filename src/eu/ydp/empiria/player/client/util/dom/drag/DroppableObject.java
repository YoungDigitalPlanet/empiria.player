package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.user.client.ui.Widget;

public interface DroppableObject<W extends Widget> extends HasDropHandlers {
	public Widget getDroppableWidget();
	public W getOriginalWidget();
	public void setDisableDrop(boolean disable);
}
