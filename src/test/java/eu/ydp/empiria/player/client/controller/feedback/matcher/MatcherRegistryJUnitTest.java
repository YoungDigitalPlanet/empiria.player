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

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MatcherRegistryJUnitTest extends AbstractTestBase {

    private MatcherRegistry registry;

    @Override
    public void setUp() {
        super.setUp();
        registry = injector.getInstance(MatcherRegistry.class);
    }

    @Test
    public void shouldReturnCorrectMatcherForFeedbackCondition() {
        assertThat(registry.getMatcher(new PropertyConditionBean()), is(instanceOf(PropertyConditionMatcher.class)));
        assertThat(registry.getMatcher(new CountConditionBean()), is(instanceOf(CountConditionMatcher.class)));
        assertThat(registry.getMatcher(new AndConditionBean()), is(instanceOf(AndConditionMatcher.class)));
        assertThat(registry.getMatcher(new OrConditionBean()), is(instanceOf(OrConditionMatcher.class)));
        assertThat(registry.getMatcher(new NotConditionBean()), is(instanceOf(NotConditionMatcher.class)));
    }

    @Test
    public void shouldReturnSameMatcherForSameFeedbackCondition() {
        FeedbackMatcher propertyMatcher1 = registry.getMatcher(new PropertyConditionBean());
        FeedbackMatcher propertyMatcher2 = registry.getMatcher(new PropertyConditionBean());

        FeedbackMatcher countMatcher1 = registry.getMatcher(new CountConditionBean());
        FeedbackMatcher countMatcher2 = registry.getMatcher(new CountConditionBean());

        FeedbackMatcher andMatcher1 = registry.getMatcher(new AndConditionBean());
        FeedbackMatcher andMatcher2 = registry.getMatcher(new AndConditionBean());

        FeedbackMatcher orMatcher1 = registry.getMatcher(new OrConditionBean());
        FeedbackMatcher orMatcher2 = registry.getMatcher(new OrConditionBean());

        FeedbackMatcher notMatcher1 = registry.getMatcher(new NotConditionBean());
        FeedbackMatcher notMatcher2 = registry.getMatcher(new NotConditionBean());

        assertThat(propertyMatcher1.equals(propertyMatcher2), is(true));
        assertThat(countMatcher1.equals(countMatcher2), is(true));
        assertThat(andMatcher1.equals(andMatcher2), is(true));
        assertThat(orMatcher1.equals(orMatcher2), is(true));
        assertThat(notMatcher1.equals(notMatcher2), is(true));
    }

}
