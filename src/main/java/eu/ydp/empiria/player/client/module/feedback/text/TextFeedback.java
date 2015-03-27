package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.user.client.ui.Widget;

public interface TextFeedback {

	void show();

	void hide();

	void setTextElement(Widget widget);

	void clearTextElement();
}
