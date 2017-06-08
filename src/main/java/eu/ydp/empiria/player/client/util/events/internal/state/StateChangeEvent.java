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

package eu.ydp.empiria.player.client.util.events.internal.state;

import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class StateChangeEvent extends AbstractEvent<StateChangeEventHandler, StateChangeEventTypes> {
    public static EventTypes<StateChangeEventHandler, StateChangeEventTypes> types = new EventTypes<StateChangeEventHandler, StateChangeEventTypes>();
    private final StateChangedInteractionEvent value;

    public StateChangeEvent(StateChangeEventTypes type, StateChangedInteractionEvent value) {
        super(type, null);
        this.value = value;
    }

    public StateChangedInteractionEvent getValue() {
        return value;
    }

    @Override
    protected EventTypes<StateChangeEventHandler, StateChangeEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(StateChangeEventHandler handler) {
        handler.onStateChange(this);
    }

    public static EventType<StateChangeEventHandler, StateChangeEventTypes> getType(StateChangeEventTypes type) {
        return types.getType(type);
    }

}
