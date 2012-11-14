package eu.ydp.empiria.player.client.controller.feedback.structure;

import java.util.List;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;

public interface Feedback {
	
	List<FeedbackAction> getActions();
	
	FeedbackCondition getCondition();
}
