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

public abstract class AbstractEvent<H, E extends Enum<E>> implements Event<H, E> {
    private final Object source;

    private final E type;

    public AbstractEvent(E type, Object source) {
        this.type = type;
        this.source = source;
    }

    public E getType() {
        return type;
    }

    @Override
    public EventType<H, E> getAssociatedType() {
        return getTypes().getType(type);
    }

    @Override
    public Object getSource() {
        return source;
    }

    protected abstract EventTypes<H, E> getTypes();
}
