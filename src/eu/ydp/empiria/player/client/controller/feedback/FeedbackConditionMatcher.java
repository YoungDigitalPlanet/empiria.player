package eu.ydp.empiria.player.client.controller.feedback;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.matcher.FeedbackMatcher;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;

public class FeedbackConditionMatcher {
	
	@Inject
	private MatcherRegistry registry;
	
	public boolean match(FeedbackCondition condition, FeedbackProperties properties){
		boolean matches = false;
		FeedbackMatcher matcher = registry.getMatcher(condition);
		
		if (matcher != null) {
			matches = matcher.match(condition, properties);
		}
		
		return matches;
	}
}
