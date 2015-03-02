package eu.ydp.empiria.player.client.controller.feedback.matcher;

import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;

public class ConditionMatcherBase {

	MatcherRegistry registry;

	public ConditionMatcherBase(MatcherRegistry registry) {
		this.registry = registry;
	}

	FeedbackMatcher getMatcher(FeedbackCondition condition) {
		return registry.getMatcher(condition);
	}
}
