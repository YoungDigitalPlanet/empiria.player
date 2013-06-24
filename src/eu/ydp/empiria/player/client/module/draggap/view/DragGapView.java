package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface DragGapView extends IsWidget {
	void setContent(String content);
	void removeContent();
	void markAnswers(boolean mark);
	void showCorrectAnswers(boolean show);
	void lock(boolean lock);
}
