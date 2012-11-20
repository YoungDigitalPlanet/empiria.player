package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.NotConditionBean;

public class NotConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

	NotConditionBean notCondition;

	@Inject
	public NotConditionMatcher(@Assisted MatcherRegistry registry) {
		super(registry);
	}

	@Override
	public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
		boolean matches = false;

		notCondition = (NotConditionBean) condition;
		FeedbackMatcher feedbackMatcher = getMatcher(notCondition.getCondition());
		matches ^= feedbackMatcher.match(notCondition.getCondition(), properties);

		return matches;
	}

}
