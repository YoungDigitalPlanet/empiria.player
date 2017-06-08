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

package eu.ydp.empiria.player.client.module.tutor;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class TutorEvent extends AbstractEvent<TutorEventHandler, TutorEventTypes> {

    private static final EventTypes<TutorEventHandler, TutorEventTypes> types = new EventTypes<TutorEventHandler, TutorEventTypes>();

    private boolean isMuted;
    private int personaIndex;

    public TutorEvent(TutorEventTypes type, Object source, int personaIndex) {
        super(type, source);
        this.personaIndex = personaIndex;
    }

    public TutorEvent(TutorEventTypes type, boolean isMuted, Object source) {
        super(type, source);
        this.isMuted = isMuted;
    }

    public int getPersonaIndex() {
        return personaIndex;
    }

    @Override
    protected EventTypes<TutorEventHandler, TutorEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(TutorEventHandler handler) {
        handler.onTutorChanged(this);
    }

    public static EventType<TutorEventHandler, TutorEventTypes> getType(TutorEventTypes type) {
        return types.getType(type);
    }

    public boolean isMuted() {
        return isMuted;
    }
}
