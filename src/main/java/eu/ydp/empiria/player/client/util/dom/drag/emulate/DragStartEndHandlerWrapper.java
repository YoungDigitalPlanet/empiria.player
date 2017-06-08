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

package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.dom.client.DataTransfer;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

public class DragStartEndHandlerWrapper extends AbstractHTML5DragDropWrapper {
    protected static class DragStartEventWrapper extends com.google.gwt.event.dom.client.DragStartEvent {
        private final DragDropSetGetData wrapper;

        public DragStartEventWrapper(DragDropSetGetData wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public DataTransfer getDataTransfer() {
            return wrapper.getDataTransfer();
        }
    }

    protected static class DragEndEventWrapper extends com.google.gwt.event.dom.client.DragEndEvent {
        private final DragDropSetGetData wrapper;

        public DragEndEventWrapper(DragDropSetGetData wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public DataTransfer getDataTransfer() {
            return wrapper.getDataTransfer();
        }
    }

    private final DraggableWidget<?> draggableWidget;

    @Override
    protected Element getElement() {
        return draggableWidget.getElement();
    }

    public DragStartEndHandlerWrapper(DraggableWidget<?> draggableWidget) {
        this.draggableWidget = draggableWidget;
    }

    public HandlerRegistration wrap(final DragStartHandler dragHandlers) {
        HandlerRegistration addDragStartHandler = draggableWidget.addDragStartHandler(new DragStartEventHandler() {
            @Override
            public void onDragStart(DragStartEvent event) {
                dragHandlers.onDragStart(new DragStartEventWrapper(DragStartEndHandlerWrapper.this));
            }
        });
        return addDragStartHandler;
    }

    public HandlerRegistration wrap(final DragEndHandler dragHandlers) {
        HandlerRegistration addDragStartHandler = draggableWidget.addDragStopHandler(new DragStopEventHandler() {

            @Override
            public void onDragStop(DragStopEvent event) {
                dragHandlers.onDragEnd(new DragEndEventWrapper(DragStartEndHandlerWrapper.this));
            }
        });
        return addDragStartHandler;
    }

}
