package eu.ydp.empiria.player.client.controller.feedback.processor;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;

public interface FeedbackActionProcessorTarget {

    boolean canProcessAction(FeedbackAction action);

    void processSingleAction(FeedbackAction action, FeedbackMark mark);

    void clearFeedback();
}
