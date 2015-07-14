package eu.ydp.empiria.player.client.controller.feedback.structure;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;

import java.util.List;

public interface Feedback {

    List<FeedbackAction> getActions();

    FeedbackCondition getCondition();
}
