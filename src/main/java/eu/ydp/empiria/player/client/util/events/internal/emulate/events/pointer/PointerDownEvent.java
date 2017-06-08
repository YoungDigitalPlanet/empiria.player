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

package eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer;

import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer.PointerDownHandler;

public class PointerDownEvent extends PointerEvent<PointerDownHandler> {

    private static final Type<PointerDownHandler> TYPE = new Type<PointerDownHandler>(PointerEventsConstants.POINTER_DOWN, new PointerDownEvent());

    protected PointerDownEvent() {
    }

    public static Type<PointerDownHandler> getType() {
        return TYPE;
    }

    @Override
    public com.google.gwt.event.dom.client.DomEvent.Type<PointerDownHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PointerDownHandler handler) {
        handler.onPointerDown(this);
    }

}
