/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounter;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class CountConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

    private final FeedbackCounter feedbackConditionCounter;

    @Inject
    public CountConditionMatcher(@Assisted MatcherRegistry registry, FeedbackCounter feedbackConditionCounter) {
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
