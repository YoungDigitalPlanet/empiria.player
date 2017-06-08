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

package eu.ydp.empiria.player.client.util.events.internal.reset;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class LessonResetEvent extends AbstractEvent<LessonResetEventHandler, LessonResetEventTypes> {
    public static EventTypes<LessonResetEventHandler, LessonResetEventTypes> types = new EventTypes<>();

    public LessonResetEvent(LessonResetEventTypes type) {
        super(type, null);
    }

    @Override
    protected EventTypes<LessonResetEventHandler, LessonResetEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(LessonResetEventHandler handler) {
        handler.onLessonReset(this);
    }

    public static EventType<LessonResetEventHandler, LessonResetEventTypes> getType(LessonResetEventTypes type) {
        return types.getType(type);
    }

}
