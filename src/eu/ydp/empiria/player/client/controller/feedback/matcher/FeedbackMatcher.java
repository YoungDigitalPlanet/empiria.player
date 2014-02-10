package eu.ydp.empiria.player.client.controller.feedback.matcher;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;

public interface FeedbackMatcher {

	boolean match(FeedbackCondition condition, FeedbackProperties properties);

}
