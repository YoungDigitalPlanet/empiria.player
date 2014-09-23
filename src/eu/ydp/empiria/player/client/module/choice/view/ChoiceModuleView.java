package eu.ydp.empiria.player.client.module.choice.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface ChoiceModuleView extends IsWidget {

	void clear();

	void addChoice(Widget widget);

	Element getPrompt();

	void enableTestSubmittedMode();

	void disableTestSubmittedMode();

}
