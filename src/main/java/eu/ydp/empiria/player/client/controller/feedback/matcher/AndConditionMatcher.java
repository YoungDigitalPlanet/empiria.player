package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;

public class AndConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

    AndConditionBean andCondition;

    @Inject
    public AndConditionMatcher(@Assisted MatcherRegistry registry) {
        super(registry);
    }

    @Override
    public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
        boolean matches = true;

        andCondition = (AndConditionBean) condition;

        for (FeedbackCondition childCondition : andCondition.getAllConditions()) {
            FeedbackMatcher feedbackMatcher = getMatcher(childCondition);
            matches = matches && feedbackMatcher.match(childCondition, properties);
            if (matches == false) {
                break;
            }
        }

        return matches;
    }

}
