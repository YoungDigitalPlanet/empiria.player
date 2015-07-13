package eu.ydp.empiria.player.client.ui.drop;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

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
