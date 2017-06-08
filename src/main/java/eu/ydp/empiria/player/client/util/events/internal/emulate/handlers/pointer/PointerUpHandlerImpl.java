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

package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer;

import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;

import javax.inject.Inject;

public class PointerUpHandlerImpl implements PointerUpHandler {

    private final TouchOnEndHandler touchOnEndHandler;
    private final PointerEventsCoordinates pointerEventsCoordinates;

    @Inject
    public PointerUpHandlerImpl(@Assisted TouchOnEndHandler touchOnEndHandler, PointerEventsCoordinates pointerEventsCoordinates) {
        this.touchOnEndHandler = touchOnEndHandler;
        this.pointerEventsCoordinates = pointerEventsCoordinates;
    }

    @Override
    public void onPointerUp(PointerUpEvent event) {
        if (event.isTouchEvent()) {
            pointerEventsCoordinates.removeEvent(event);
            touchOnEndHandler.onEnd(event.getNativeEvent());
        }
    }

}
