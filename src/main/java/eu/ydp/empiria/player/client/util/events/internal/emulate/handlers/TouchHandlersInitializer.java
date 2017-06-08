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

package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchCancelHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchEndHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch.TouchStartHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;

public class TouchHandlersInitializer implements ITouchHandlerInitializer {

    @Override
    public void addTouchMoveHandler(final TouchOnMoveHandler touchOnMoveHandler, Widget listenOn) {
        listenOn.addDomHandler(new TouchMoveHandlerImpl(touchOnMoveHandler), TouchMoveEvent.getType());
    }

    @Override
    public void addTouchStartHandler(final TouchOnStartHandler touchStartHandler, Widget listenOn) {
        listenOn.addDomHandler(new TouchStartHandlerImpl(touchStartHandler), TouchStartEvent.getType());

    }

    @Override
    public void addTouchEndHandler(final TouchOnEndHandler touchEndHandler, Widget listenOn) {
        listenOn.addDomHandler(new TouchEndHandlerImpl(touchEndHandler), TouchEndEvent.getType());

    }

    @Override
    public void addTouchCancelHandler(final TouchOnCancelHandler touchCancelHandler, Widget listenOn) {
        listenOn.addDomHandler(new TouchCancelHandlerImpl(touchCancelHandler), TouchCancelEvent.getType());
    }
}
