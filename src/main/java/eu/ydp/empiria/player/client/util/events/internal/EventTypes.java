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

package eu.ydp.empiria.player.client.util.events.internal;

import java.util.HashMap;
import java.util.Map;

public class EventTypes<H, E extends Enum<E>> {
    public Map<E, EventType<H, E>> types = new HashMap<E, EventType<H, E>>();

    public EventType<H, E> getType(E type) {
        if (!types.containsKey(type)) {
            types.put(type, new EventType<H, E>(type));
        }
        return types.get(type);
    }

    public EventType<H, E>[] getTypes(E[] types) {
        EventType<H, E>[] array = new EventType[types.length];
        for (int x = 0; x < types.length; ++x) {
            array[x] = getType(types[x]);
        }
        return array;
    }

}
