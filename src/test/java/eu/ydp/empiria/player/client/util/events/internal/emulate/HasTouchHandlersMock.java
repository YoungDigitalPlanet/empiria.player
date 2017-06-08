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

import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.TouchHandler;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class HasTouchHandlersMock extends AbstractEventHandler<TouchHandler, TouchTypes, TouchEvent> implements HasTouchHandlers {

    @Override
    public HandlerRegistration addTouchHandler(TouchHandler handler, EventType<TouchHandler, TouchTypes> event) {
        return addHandler(handler, event);
    }

    @Override
    protected void dispatchEvent(TouchHandler handler, TouchEvent event) {
        handler.onTouchEvent(event);
    }

    public void emulateEvent(TouchEvent event) {
        fireEvent(event);
    }

    @Override
    public HandlerRegistration[] addTouchHandlers(TouchHandler handler, EventType<TouchHandler, TouchTypes>... event) {
        HandlerRegistration[] reg = new HandlerRegistration[event.length];
        for (int x = 0; x < reg.length; ++x) {
            reg[x] = addHandler(handler, event[x]);
        }
        return reg;
    }

}
