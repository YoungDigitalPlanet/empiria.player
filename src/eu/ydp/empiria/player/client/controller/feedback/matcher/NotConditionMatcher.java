package eu.ydp.empiria.player.client.controller.feedback.matcher;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.NotConditionBean;

public class NotConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {
	
	NotConditionBean notCondition;

	@Override
	public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
		boolean matches = false;
		
		notCondition = (NotConditionBean) condition;
		FeedbackMatcher feedbackMatcher = getMatcher(notCondition.getCondition());
		matches = !feedbackMatcher.match(notCondition.getCondition(), properties);
		
		return matches;
	}

}
