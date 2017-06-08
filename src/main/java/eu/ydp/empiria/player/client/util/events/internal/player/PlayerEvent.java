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

package eu.ydp.empiria.player.client.util.events.internal.player;

import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class PlayerEvent extends AbstractEvent<PlayerEventHandler, PlayerEventTypes> {
    public static EventTypes<PlayerEventHandler, PlayerEventTypes> types = new EventTypes<PlayerEventHandler, PlayerEventTypes>();
    private final Object value;

    public PlayerEvent(PlayerEventTypes type, Object value, Object source) {
        super(type, source);
        this.value = value;
    }

    public PlayerEvent(PlayerEventTypes type) {
        this(type, null, null);
    }

    public Object getValue() {
        return value;
    }

    @Override
    protected EventTypes<PlayerEventHandler, PlayerEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(PlayerEventHandler handler) {
        handler.onPlayerEvent(this);
    }

    public static EventType<PlayerEventHandler, PlayerEventTypes> getType(PlayerEventTypes type) {
        return types.getType(type);
    }

    public static EventType<PlayerEventHandler, PlayerEventTypes>[] getTypes(PlayerEventTypes... typeList) {
        return types.getTypes(typeList);
    }

}
