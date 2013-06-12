package eu.ydp.empiria.player.client.module.choice.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

public interface SimpleChoiceView extends IsWidget {
	void markCorrect();

	void markWrong();

	void unmarkCorrect();

	void unmarkWrong();

	Widget getFeedbackPlaceHolder();

	void reset();

	void setLocked(boolean locked);
	
	void setButton(ChoiceButtonBase button);

	void setContent(Widget contentWidget);

	void setSelected(boolean select);

	boolean isSelected();
}
