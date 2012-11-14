package eu.ydp.empiria.player.client.controller.feedback.structure;

import java.util.List;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;

public interface Feedback {
	
	List<FeedbackAction> getActions();
	
	FeedbackCondition getCondition();
}
