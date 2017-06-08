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

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEvent;
import eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEventHandler;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.Collection;
import java.util.Map;

import static eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEvent.getType;
import static eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEventTypes.RESET_COUNTER;

public class FeedbackCounter implements FeedbackCounterEventHandler {
    private final FeedbackCounterCleaner counterCleaner;
    private Map<FeedbackCountable, Integer> map = Maps.newHashMap();

    @Inject
    public FeedbackCounter(FeedbackCounterCleaner counterCleaner, EventsBus eventsBus) {
        this.counterCleaner = counterCleaner;
        eventsBus.addHandler(getType(RESET_COUNTER), this);
    }

    public void add(FeedbackCountable feedbackCountable) {
        int count = 1;

        if (map.containsKey(feedbackCountable)) {
            count = map.get(feedbackCountable) + 1;
        }
        map.put(feedbackCountable, count);
    }

    public int getCount(FeedbackCountable feedbackCountable) {
        if (map.containsKey(feedbackCountable)) {
            return map.get(feedbackCountable);
        }

        return 0;
    }

    @Override
    public void onFeedbackCounterEvent(FeedbackCounterEvent event) {
        IModule sourceModule = event.getSourceModule();
        Collection<FeedbackCountable> objectsToRemove = counterCleaner.getObjectsToRemove(sourceModule);
        for (FeedbackCountable t : objectsToRemove) {
            map.remove(t);
        }
    }
}
