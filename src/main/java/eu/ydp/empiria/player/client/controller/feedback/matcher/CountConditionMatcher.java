package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackConditionCounter;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class CountConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

    private final FeedbackConditionCounter feedbackConditionCounter;

    @Inject
    public CountConditionMatcher(@Assisted MatcherRegistry registry, FeedbackConditionCounter feedbackConditionCounter) {
        super(registry);
        this.feedbackConditionCounter = feedbackConditionCounter;
    }

    @Override
    public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
        boolean matches = false;

        if (condition instanceof CountConditionBean) {
            CountConditionBean countCondition = (CountConditionBean) condition;
            FeedbackCondition childCondition = countCondition.getCondition();
            FeedbackMatcher feedbackMatcher = getMatcher(childCondition);
            boolean match = feedbackMatcher.match(childCondition, properties);
            if (match) {
                feedbackConditionCounter.add(countCondition);
                MatchOperator operator = MatchOperator.getOperator(countCondition.getOperator());
                matches = operator.match(getCountValue(countCondition), countCondition.getCount());
            }
        }

        return matches;
    }

    private Integer getCountValue(FeedbackCondition condition) {
        return feedbackConditionCounter.getCount(condition);
    }
}
