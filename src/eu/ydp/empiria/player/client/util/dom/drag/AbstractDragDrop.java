package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractDragDrop<W extends Widget> {

	protected void addStyleForWidget(String style, boolean disabled) {
		if (!disabled) {
			getOriginalWidget().addStyleName(style);
		}
	}

	protected void removeStyleForWidget(String style, boolean disabled) {
		if (!disabled) {
			getOriginalWidget().removeStyleName(style);
		}
	}

	protected abstract W getOriginalWidget();

	protected abstract void setDisableDrop(boolean disabled);
}
