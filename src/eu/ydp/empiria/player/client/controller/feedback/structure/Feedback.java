package eu.ydp.empiria.player.client.controller.feedback.structure;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackActionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackConditionBean;

public interface Feedback {
	
	FeedbackActionBean getAction();
	
	FeedbackConditionBean getCondition();
}
