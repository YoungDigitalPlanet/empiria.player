package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasDropHandlers {
    public HandlerRegistration addDragEnterHandler(DragEnterHandler handler);

    public HandlerRegistration addDragLeaveHandler(DragLeaveHandler handler);

    public HandlerRegistration addDropHandler(DropHandler handler);

    public HandlerRegistration addDragOverHandler(DragOverHandler handler);
}
