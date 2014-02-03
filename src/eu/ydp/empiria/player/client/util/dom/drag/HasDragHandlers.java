package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasDragHandlers {

	public HandlerRegistration addDragEndHandler(DragEndHandler handler);

	public HandlerRegistration addDragStartHandler(DragStartHandler handler);

	public HandlerRegistration addDropHandler(DropHandler handler);

}
