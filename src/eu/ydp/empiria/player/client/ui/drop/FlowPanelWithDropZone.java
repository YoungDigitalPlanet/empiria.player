package eu.ydp.empiria.player.client.ui.drop;

import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public class FlowPanelWithDropZone extends DroppableWidget<FlowPanel> {

	public FlowPanelWithDropZone() {
		initWidget(new FlowPanel());
	}

	public void add(IsWidget child) {
		this.getOriginalWidget().add(child);
	}

	public void clear() {
		this.getOriginalWidget().clear();
	}
}
