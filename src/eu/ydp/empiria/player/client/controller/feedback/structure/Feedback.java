package eu.ydp.empiria.player.client.controller.feedback.structure;

import java.util.List;

public interface Feedback {
	
	List<FeedbackAction> getActions();
	
	FeedbackCriterion getCriterion();
	
}
