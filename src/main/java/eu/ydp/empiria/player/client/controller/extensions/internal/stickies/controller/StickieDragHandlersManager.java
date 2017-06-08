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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.geom.Point;

import java.util.ArrayList;
import java.util.List;

public class StickieDragHandlersManager {

    private final IPlayerContainersAccessor accessor;
    private final EventsBus eventsBus;
    private StickieDragController stickieDragController;

    private List<HandlerRegistration> upMoveHandlersRegistrations = new ArrayList<HandlerRegistration>();

    @Inject
    public StickieDragHandlersManager(@Assisted StickieDragController stickieDragController, IPlayerContainersAccessor accessor, EventsBus eventsBus) {
        this.stickieDragController = stickieDragController;
        this.accessor = accessor;
        this.eventsBus = eventsBus;
    }

    public void mouseDown(MouseDownEvent event) {
        event.preventDefault();
        Point<Integer> point = new Point<Integer>(event.getScreenX(), event.getScreenY());
        onDragStart(point);
        registerMouseMoveHandler();
        registerMouseUpHandler();
    }

    public void touchStart(TouchStartEvent event) {
        Point<Integer> touchPoint = getTouchPoint(event.getTouches());
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));

        onDragStart(touchPoint);
        addTouchMoveHandler();
        addTouchEndHandler();
    }

    private void registerMouseUpHandler() {
        Type<MouseUpHandler> type = MouseUpEvent.getType();
        registerMouseHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp(MouseUpEvent event) {
                onDragEnd();
            }

        }, type);
    }

    private void removeMoveAndUpHandlersIfExists() {
        for (HandlerRegistration handlerRegistration : upMoveHandlersRegistrations) {
            handlerRegistration.removeHandler();
        }
        upMoveHandlersRegistrations.clear();
    }

    private void registerMouseMoveHandler() {
        registerMouseHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove(MouseMoveEvent event) {
                Point<Integer> mouseMovePoint = new Point<Integer>(event.getScreenX(), event.getScreenY());
                onDragMove(event, mouseMovePoint);
            }
        }, MouseMoveEvent.getType());
    }

    private void addTouchEndHandler() {
        registerTouchHandler(new TouchEndHandler() {

            @Override
            public void onTouchEnd(TouchEndEvent event) {
                onDragEnd();
            }

        }, TouchEndEvent.getType());
    }

    private void addTouchMoveHandler() {
        registerTouchHandler(new TouchMoveHandler() {

            @Override
            public void onTouchMove(TouchMoveEvent event) {
                Point<Integer> touchMovePoint = getTouchPoint(event.getTouches());
                onDragMove(event, touchMovePoint);
            }
        }, TouchMoveEvent.getType());
    }

    private void onDragStart(Point<Integer> point) {
        stickieDragController.dragStart(point);
        removeMoveAndUpHandlersIfExists();
    }

    private void onDragMove(DomEvent<?> event, Point<Integer> movePosition) {
        stickieDragController.dragMove(movePosition);
        event.preventDefault();
    }

    private void onDragEnd() {
        stickieDragController.dragEnd();
        removeMoveAndUpHandlersIfExists();
    }

    private Point<Integer> getTouchPoint(JsArray<Touch> touches) {
        Touch touch = touches.get(0);
        Point<Integer> point = new Point<Integer>(touch.getScreenX(), touch.getScreenY());
        return point;
    }

    private <H extends EventHandler> HandlerRegistration registerMouseHandler(H handler, Type<H> type) {
        HandlerRegistration registration = ((Widget) accessor.getPlayerContainer()).addDomHandler(handler, type);

        upMoveHandlersRegistrations.add(registration);
        return registration;
    }

    private <H extends EventHandler> HandlerRegistration registerTouchHandler(H handler, Type<H> type) {
        HandlerRegistration registration = RootPanel.get().addDomHandler(handler, type);

        upMoveHandlersRegistrations.add(registration);
        return registration;
    }
}
