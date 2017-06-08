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

public class EventType<H, T extends Enum<T>> {
    private static int nextHashCode;
    private final int index;
    private final Enum<T> type;

    public EventType(Enum<T> type) {
        nextHashCode += type.ordinal();
        index = ++nextHashCode;
        this.type = type;
    }

    public Enum<T> getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type [index=");
        builder.append(index);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EventType<?, ?> other = (EventType<?, ?>) obj;
        if (type == null) {
            return other.type == null;
        }
        return type.equals(other.type);
    }
}
