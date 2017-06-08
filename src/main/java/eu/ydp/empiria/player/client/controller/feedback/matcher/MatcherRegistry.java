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

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.*;

import java.util.Map;

@Singleton
public class MatcherRegistry {

    Map<Class<? extends FeedbackCondition>, FeedbackMatcher> map;

    @Inject
    private MatcherRegistryFactory factory;

    public void init() {
        map = Maps.newHashMap();
        map.put(CountConditionBean.class, factory.getCountConditionMatcher(this));
        map.put(PropertyConditionBean.class, factory.getPropertyConditionMatcher(this));
        map.put(AndConditionBean.class, factory.getAndConditionMatcher(this));
        map.put(OrConditionBean.class, factory.getOrConditionMatcher(this));
        map.put(NotConditionBean.class, factory.getNotConditionMatcher(this));
    }

    public FeedbackMatcher getMatcher(FeedbackCondition condition) {
        if (map == null) {
            init();
        }
        return map.get(condition.getClass());
    }

}
