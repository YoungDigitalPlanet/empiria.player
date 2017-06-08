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

package eu.ydp.empiria.player.client.util.events.internal.feedback;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class FeedbackEvent extends AbstractEvent<FeedbackEventHandler, FeedbackEventTypes> {

    private static final EventTypes<FeedbackEventHandler, FeedbackEventTypes> types = new EventTypes<FeedbackEventHandler, FeedbackEventTypes>();

    private boolean isMuted;

    public FeedbackEvent(FeedbackEventTypes type, Object source) {
        super(type, source);
    }

    public FeedbackEvent(FeedbackEventTypes type, boolean isMuted, Object source) {
        super(type, source);
        this.isMuted = isMuted;
    }

    @Override
    protected EventTypes<FeedbackEventHandler, FeedbackEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(FeedbackEventHandler handler) {
        handler.onFeedbackEvent(this);
    }

    public static EventType<FeedbackEventHandler, FeedbackEventTypes> getType(FeedbackEventTypes type) {
        return types.getType(type);
    }

    public boolean isMuted() {
        return isMuted;
    }

}
