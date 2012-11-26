package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class CountConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

	private CountConditionBean condition;

	private MatchOperator operator;
	@Inject
	public CountConditionMatcher(@Assisted MatcherRegistry registry) {
		super(registry);
	}
	@Override
	public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
		boolean matches = false;

		if (condition instanceof CountConditionBean) {
			this.condition = (CountConditionBean) condition;
			this.operator = MatchOperator.getOperator(this.condition.getOperator());
			matches = operator.match(getCountValue(this.condition.getCondition()), this.condition.getCount());
		}

		return matches;
	}

	private Integer getCountValue(FeedbackCondition condition) {
		return 0;
	}
}
