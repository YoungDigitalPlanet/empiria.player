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

package eu.ydp.empiria.player.client.controller.feedback.counter.event;

import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class FeedbackCounterEvent extends AbstractEvent<FeedbackCounterEventHandler, FeedbackCounterEventTypes> {
    public static EventTypes<FeedbackCounterEventHandler, FeedbackCounterEventTypes> types = new EventTypes<>();
    private final IModule module;

    public FeedbackCounterEvent(FeedbackCounterEventTypes type, IModule module) {
        super(type, module);
        this.module = module;
    }

    public IModule getSourceModule(){
        return module;
    }

    @Override
    protected EventTypes<FeedbackCounterEventHandler, FeedbackCounterEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(FeedbackCounterEventHandler handler) {
        handler.onFeedbackCounterEvent(this);
    }

    public static EventType<FeedbackCounterEventHandler, FeedbackCounterEventTypes> getType(FeedbackCounterEventTypes type) {
        return types.getType(type);
    }
}
