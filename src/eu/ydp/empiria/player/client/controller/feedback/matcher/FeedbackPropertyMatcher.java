package eu.ydp.empiria.player.client.controller.feedback.matcher;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackCondition;

public interface FeedbackPropertyMatcher {

	boolean match(FeedbackCondition condition, FeedbackProperties properties);
	
}
