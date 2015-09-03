package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class CountConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

    private final CountFeedbackProperties countFeedbackProperties;
    private CountConditionBean condition;

    private MatchOperator operator;

    @Inject
    public CountConditionMatcher(@Assisted MatcherRegistry registry, CountFeedbackProperties countFeedbackProperties) {
        super(registry);
        this.countFeedbackProperties = countFeedbackProperties;
    }

    @Override
    public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
        boolean matches = false;

        if (condition instanceof CountConditionBean) {
            this.condition = (CountConditionBean) condition;
            FeedbackCondition childCondition = this.condition.getCondition();
            FeedbackMatcher feedbackMatcher = getMatcher(childCondition);
            boolean match = feedbackMatcher.match(childCondition, properties);
            if (match) {
                countFeedbackProperties.add(condition);
                this.operator = MatchOperator.getOperator(this.condition.getOperator());
                matches = operator.match(getCountValue(condition), this.condition.getCount());
            }
        }

        return matches;
    }

    private Integer getCountValue(FeedbackCondition condition) {
        return countFeedbackProperties.getCount(condition);
    }
}
