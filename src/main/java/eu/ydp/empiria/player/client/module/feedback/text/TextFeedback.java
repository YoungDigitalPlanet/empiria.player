package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;

public interface TextFeedback extends IsWidget {

    void addFeedback(Widget widget, FeedbackMark mark);

    void hideModule();

    void showFeedback();

    void hideFeedback();

    void addCloseButtonClickHandler(ClickHandler handler);

    void addShowButtonClickHandler(ClickHandler handler);
}
