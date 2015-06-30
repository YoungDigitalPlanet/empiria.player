package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.dom.client.DataTransfer;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

public class DropEventsHandlerWrapper extends AbstractHTML5DragDropWrapper {

    protected class DragEnterEventWrapper extends DragEnterEvent {
        private final DragDropSetGetData setGetData;

        public DragEnterEventWrapper(DragDropSetGetData data) {
            this.setGetData = data;
        }

        @Override
        public DataTransfer getDataTransfer() {
            return setGetData.getDataTransfer();
        }
    }

    protected class DragLeaveEventWrapper extends DragLeaveEvent {
        private final DragDropSetGetData setGetData;

        public DragLeaveEventWrapper(DragDropSetGetData data) {
            this.setGetData = data;
        }

        @Override
        public DataTransfer getDataTransfer() {
            return setGetData.getDataTransfer();
        }
    }

    protected class DragOverEventWrapper extends DragOverEvent {
        private final DragDropSetGetData setGetData;

        public DragOverEventWrapper(DragDropSetGetData data) {
            this.setGetData = data;
        }

        @Override
        public DataTransfer getDataTransfer() {
            return setGetData.getDataTransfer();
        }
    }

    protected class DropEventWrapper extends com.google.gwt.event.dom.client.DropEvent {
        private final DragDropSetGetData setGetData;

        public DropEventWrapper(DragDropSetGetData data, NativeEvent nativeEvent) {
            this.setGetData = data;
            setNativeEvent(nativeEvent);
        }

        @Override
        public DataTransfer getDataTransfer() {
            return setGetData.getDataTransfer();
        }

        @Override
        public void stopPropagation() {
        }

        @Override
        public void preventDefault() {
        }
    }

    private final DroppableWidget<?> droppableWidget;
    private final OverlayTypesParser overlayTypesParser;

    public DropEventsHandlerWrapper(DroppableWidget<?> droppableWidget, OverlayTypesParser overlayTypesParser) {
        this.droppableWidget = droppableWidget;
        this.overlayTypesParser = overlayTypesParser;
    }

    public HandlerRegistration wrap(final DragEnterHandler dragEnterHandler) {
        return droppableWidget.addOverDroppableHandler(new OverDroppableEventHandler() {
            @Override
            public void onOverDroppable(OverDroppableEvent event) {
                dragEnterHandler.onDragEnter(new DragEnterEventWrapper(DropEventsHandlerWrapper.this));
            }
        });
    }

    public HandlerRegistration wrap(final DragLeaveHandler dragLeaveHandler) {
        return droppableWidget.addOutDroppableHandler(new OutDroppableEventHandler() {

            @Override
            public void onOutDroppable(OutDroppableEvent event) {
                dragLeaveHandler.onDragLeave(new DragLeaveEventWrapper(DropEventsHandlerWrapper.this));
            }
        });
    }

    public HandlerRegistration wrap(final DragOverHandler dragOverHandler) {
        return droppableWidget.addOverDroppableHandler(new OverDroppableEventHandler() {

            @Override
            public void onOverDroppable(OverDroppableEvent event) {
                dragOverHandler.onDragOver(new DragOverEventWrapper(DropEventsHandlerWrapper.this));
            }
        });
    }

    public HandlerRegistration wrap(final DropHandler dropHandler) {
        return droppableWidget.addDropHandler(new DropEventHandler() {

            @Override
            public void onDrop(DropEvent event) {
                dropHandler.onDrop(new DropEventWrapper(DropEventsHandlerWrapper.this, overlayTypesParser.<NativeEvent>get()));
            }
        });
    }

    @Override
    protected Element getElement() {
        return null;
    }

}
