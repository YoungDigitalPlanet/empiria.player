package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.OrConditionBean;

public class OrConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

	OrConditionBean orCondition;

	@Inject
	public OrConditionMatcher(@Assisted MatcherRegistry registry) {
		super(registry);
	}

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
