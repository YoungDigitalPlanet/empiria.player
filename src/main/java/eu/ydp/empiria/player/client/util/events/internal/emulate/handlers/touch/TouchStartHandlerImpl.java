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

package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;

public class TouchStartHandlerImpl implements TouchStartHandler {

    private final TouchOnStartHandler touchStartHandler;

    public TouchStartHandlerImpl(final TouchOnStartHandler touchStartHandler) {
        this.touchStartHandler = touchStartHandler;
    }

    @Override
    public void onTouchStart(TouchStartEvent event) {
        touchStartHandler.onStart(event.getNativeEvent());
    }
}
