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

package eu.ydp.empiria.player.client.util.events.internal.emulate;

import com.google.gwt.dom.client.NativeEvent;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.TouchHandler;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

/**
 * emulacja zdarzen touch
 */
public class TouchEvent extends AbstractEvent<TouchHandler, TouchTypes> {
    public static EventTypes<TouchHandler, TouchTypes> types = new EventTypes<TouchHandler, TouchTypes>();

    private final NativeEvent nativeEvent;

    public TouchEvent(TouchTypes type, NativeEvent nativeEvent) {
        super(type, nativeEvent);
        this.nativeEvent = nativeEvent;
    }

    @Override
    protected EventTypes<TouchHandler, TouchTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(TouchHandler handler) {
        handler.onTouchEvent(this);
    }

    public static EventType<TouchHandler, TouchTypes> getType(TouchTypes type) {
        return types.getType(type);
    }

    public static EventType<TouchHandler, TouchTypes>[] getTypes(TouchTypes... typeList) {
        return typeList.length > 0 ? types.getTypes(typeList) : new EventType[0];

    }

    public NativeEvent getNativeEvent() {
        return nativeEvent;
    }

    public void preventDefault() {
        nativeEvent.preventDefault();
    }

}
