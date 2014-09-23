package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public interface DragGapView extends IsWidget {

	void removeContent();

	void lock(boolean lock);

	void setDragDisabled(boolean disabled);

	void updateStyle(UserAnswerType answerType);

	void setDragStartHandler(DragStartHandler dragGapStartDragHandler);

	void setDragEndHandler(DragEndHandler dragEndHandler);

	DroppableObject<FlowPanelWithDropZone> enableDropCapabilities();

	void setHeight(int height);

	void setWidth(int width);

	void setItemContent(SourcelistItemValue item);

	void enableTestSubmittedMode();

	void disableTestSubmittedMode();
}
