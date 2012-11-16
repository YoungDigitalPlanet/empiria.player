package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;

public class ConditionMatcherBase {

	@Inject
	MatcherRegistry registry;
	
	FeedbackMatcher getMatcher(FeedbackCondition condition) {
		return registry.getMatcher(condition);
	}
}
