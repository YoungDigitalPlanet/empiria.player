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

package eu.ydp.empiria.player.client.util.events.internal.html5;

import com.google.gwt.event.dom.client.DomEvent;

import java.util.HashMap;
import java.util.Map;

public class HTML5MediaEvent extends DomEvent<HTML5MediaEventHandler> {// NOPMD
    public static Map<HTML5MediaEventsType, Type<HTML5MediaEventHandler>> types = new HashMap<HTML5MediaEventsType, DomEvent.Type<HTML5MediaEventHandler>>();

    HTML5MediaEventsType type = null;

    private HTML5MediaEvent(HTML5MediaEventsType type) {
        this.type = type;
    }

    public HTML5MediaEventsType getType() {
        return type;
    }

    private static void checkIsPresent(HTML5MediaEventsType type) {
        if (!types.containsKey(type)) {
            types.put(type, new Type<HTML5MediaEventHandler>(type.name(), new HTML5MediaEvent(type)));
        }
    }

    @Override
    public com.google.gwt.event.dom.client.DomEvent.Type<HTML5MediaEventHandler> getAssociatedType() {
        checkIsPresent(type);
        return types.get(type);
    }

    @Override
    protected void dispatch(HTML5MediaEventHandler handler) {
        handler.onEvent(this);

    }

    public static Type<HTML5MediaEventHandler> getType(HTML5MediaEventsType type) {
        checkIsPresent(type);
        return types.get(type);
    }

}
