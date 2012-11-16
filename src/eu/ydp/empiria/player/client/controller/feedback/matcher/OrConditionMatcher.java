package eu.ydp.empiria.player.client.controller.feedback.matcher;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.OrConditionBean;

public class OrConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

	OrConditionBean orCondition;
	
	@Override
	public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
		boolean matches = false;
		
		orCondition = (OrConditionBean) condition;
		
		for (FeedbackCondition childCondition : orCondition.getAllConditions()) {
			FeedbackMatcher feedbackMatcher = getMatcher(childCondition);
			matches = matches || feedbackMatcher.match(childCondition, properties);
		}
		
		return matches;
	}

}
