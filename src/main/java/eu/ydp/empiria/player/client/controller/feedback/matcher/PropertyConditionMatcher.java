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
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class PropertyConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {

    private PropertyConditionBean condition;

    private FeedbackProperties properties;

    private MatchOperator operator;

    @Inject
    public PropertyConditionMatcher(@Assisted MatcherRegistry registry) {
        super(registry);
    }

    @Override
    public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
        boolean matches = false;

        if (condition instanceof PropertyConditionBean) {
            this.properties = properties;
            this.condition = (PropertyConditionBean) condition;
            this.operator = MatchOperator.getOperator(this.condition.getOperator());
            matches = checkPropertyCondition();
        }

        return matches;
    }

    protected boolean checkPropertyCondition() {
        boolean matches = false;

        if (properties.hasValue(condition.getProperty())) {
            matches = matchPropertyValue(properties.getProperty(condition.getProperty()));
        }

        return matches;
    }

    protected boolean matchPropertyValue(Object value) {
        boolean matches = false;

        if (value instanceof Boolean) {
            matches = operator.match((Boolean) value, Boolean.valueOf(condition.getValue()));
        } else if (value instanceof String) {
            matches = operator.match((String) value, condition.getValue());
        } else if (value instanceof Double) {
            matches = operator.match((Double) value, Double.valueOf(condition.getValue()));
        } else if (value instanceof Integer) {
            matches = operator.match((Integer) value, Integer.valueOf(condition.getValue()));
        }

        return matches;
    }
}
