package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public interface TextFeedback {

    void addFeedback(Widget widget);

    void show();

    void hide();

    void addCloseButtonClickHandler(ClickHandler handler);

    void addShowButtonClickHandler(ClickHandler handler);
}
