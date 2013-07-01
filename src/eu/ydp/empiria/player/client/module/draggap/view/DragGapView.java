package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public interface DragGapView extends IsWidget {
	
	void setContent(String content);

	void removeContent();

	void lock(boolean lock);

	void setDragDisabled(boolean disabled);

	void updateStyle(UserAnswerType answerType);
	
	void setDropHandler(DragGapDropHandler dragGapDropHandler);
	
	void setDragStartHandler(DragGapStartDragHandler dragGapStartDragHandler);
}
