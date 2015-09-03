package eu.ydp.empiria.player.client.module.feedback.image;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;

public interface ImageFeedback extends IsWidget{

    void setUrl(String text);

    void show(FeedbackMark mark);

    void hide();

}
