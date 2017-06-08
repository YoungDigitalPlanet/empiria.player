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

package eu.ydp.empiria.player.client.controller.feedback.counter;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.module.core.base.IModule;

import java.util.Collection;
import java.util.List;

public class FeedbackCounterCleaner {

    private static final Function<Feedback, List<FeedbackCountable>> FEEDBACK_TO_COUNTABLE_MAPPER = new Function<Feedback, List<FeedbackCountable>>() {
        @Override
        public List<FeedbackCountable> apply(Feedback feedback) {
            List<FeedbackCountable> feedbackCountables = Lists.newArrayList();
            feedbackCountables.addAll(feedback.getActions());
            feedbackCountables.add(feedback.getCondition());
            return feedbackCountables;
        }
    };

    private final FeedbackRegistry feedbackRegistry;

    @Inject
    public FeedbackCounterCleaner(FeedbackRegistry feedbackRegistry) {
        this.feedbackRegistry = feedbackRegistry;
    }

    public Collection<FeedbackCountable> getObjectsToRemove(IModule iModule) {
        return FluentIterable.from(feedbackRegistry.getModuleFeedbacks(iModule))
                .transformAndConcat(FEEDBACK_TO_COUNTABLE_MAPPER)
                .toList();
    }
}
