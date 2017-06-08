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

package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchEndHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchStartHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

public class TouchHandlersOnImageInitializer implements ITouchHandlerOnImageInitializer {

    @Inject
    private TouchHandlerFactory touchHandlerFactory;

    @Override
    public void addTouchOnImageMoveHandler(final TouchOnImageMoveHandler touchOnMoveHandler, Widget listenOn) {
        TouchMoveHandlerOnImage touchMoveHandlerOnImage = touchHandlerFactory.createTouchMoveHandlerOnImage(touchOnMoveHandler);
        listenOn.addDomHandler(touchMoveHandlerOnImage, TouchMoveEvent.getType());
    }

    @Override
    public void addTouchOnImageStartHandler(final TouchOnImageStartHandler touchStartHandler, Widget listenOn) {
        TouchStartHandlerOnImage touchStartHandlerOnImage = touchHandlerFactory.createTouchStartHandlerOnImage(touchStartHandler);
        listenOn.addDomHandler(touchStartHandlerOnImage, TouchStartEvent.getType());
    }

    @Override
    public void addTouchOnImageEndHandler(final TouchOnImageEndHandler touchEndHandler, Widget listenOn) {
        TouchEndHandlerOnImage touchEndHandlerOnImage = touchHandlerFactory.createTouchEndHandlerOnImage(touchEndHandler);
        listenOn.addDomHandler(touchEndHandlerOnImage, TouchEndEvent.getType());
    }
}
