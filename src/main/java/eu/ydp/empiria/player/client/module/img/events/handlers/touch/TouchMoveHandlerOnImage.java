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

package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;

public class TouchMoveHandlerOnImage implements TouchMoveHandler {

    private final TouchToImageEvent touchToImageEvent;

    private final TouchOnImageMoveHandler touchOnImageMoveHandler;

    @Inject
    public TouchMoveHandlerOnImage(@Assisted final TouchOnImageMoveHandler touchOnMoveHandler, final TouchToImageEvent touchEventsCoordinates) {
        this.touchOnImageMoveHandler = touchOnMoveHandler;
        this.touchToImageEvent = touchEventsCoordinates;
    }

    @Override
    public void onTouchMove(TouchMoveEvent event) {
        event.preventDefault();
        touchOnImageMoveHandler.onMove(touchToImageEvent.getTouchOnImageEvent(event));
    }

}
