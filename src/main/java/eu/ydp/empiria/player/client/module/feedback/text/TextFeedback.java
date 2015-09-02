package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;

public interface TextFeedback {

    void show(Widget widget, FeedbackMark mark);

    void hide();

    void addCloseButtonClickHandler(ClickHandler handler);
}
