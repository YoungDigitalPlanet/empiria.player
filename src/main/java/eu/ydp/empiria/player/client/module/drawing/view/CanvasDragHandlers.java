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

package eu.ydp.empiria.player.client.module.drawing.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class CanvasDragHandlers {

    private final EventsBus eventsBus;

    @Inject
    private UserInteractionHandlerFactory handlerFactory;
    @Inject
    private PositionHelper positionHelper;

    @Inject
    public CanvasDragHandlers(EventsBus eventsBus) {
        this.eventsBus = eventsBus;
    }

    public void addHandlersToView(final CanvasPresenter canvasPresenter, final Canvas canvas) {
        handlerFactory.createUserDownHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
                event.preventDefault();
                Point point = positionHelper.getPoint(event, canvas);
                canvasPresenter.mouseDown(point);
            }
        }).apply(canvas);

        handlerFactory.createUserOutHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                canvasPresenter.mouseOut();
            }
        }).apply(canvas);

        handlerFactory.createUserMoveHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                Point point = positionHelper.getPoint(event, canvas);
                canvasPresenter.mouseMove(point);
            }
        }).apply(canvas);

        handlerFactory.createUserUpHandler(new Command() {
            @Override
            public void execute(NativeEvent event) {
                canvasPresenter.mouseUp();
            }
        }).apply(canvas);
    }
}
