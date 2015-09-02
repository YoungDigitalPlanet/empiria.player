package eu.ydp.empiria.player.client.module.feedback.image;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;

public interface ImageFeedback {

    void setUrl(String text);

    String getUrl();

    void show(FeedbackMark mark);

    void hide();

}
