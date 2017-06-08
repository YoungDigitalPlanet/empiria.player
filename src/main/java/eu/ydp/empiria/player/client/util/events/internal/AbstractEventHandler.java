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

import eu.ydp.gwtutil.client.event.EventHandlerRegistrator;
import eu.ydp.gwtutil.client.event.HandlerRegistration;

import java.util.Set;

public abstract class AbstractEventHandler<H extends EventHandler, E extends Enum<E>, EV extends Event<H, E>> extends EventHandlerRegistrator<H, EventType<H, E>> {
    protected HandlerRegistration[] addHandlers(final H handler, final EventType<H, E>[] event) {
        HandlerRegistration[] registrations = new HandlerRegistration[event.length];
        for (int x = 0; x < event.length; ++x) {
            registrations[x] = addHandler(handler, event[x]);
        }
        return registrations;
    }

    protected void fireEvent(EV event) {
        final Set<H> eventHandlers = getHandlersAccordingToRunningMode(event.getAssociatedType());
        for (H handler : eventHandlers) {
            dispatchEvent(handler, event);
        }
    }

    protected abstract void dispatchEvent(H handler, EV event);

}
