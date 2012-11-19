package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.util.dom.drag.DragDropType;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

/**
 * For mobile and old browsers
 *
 */
public class EmulatedDragDrop<W extends Widget> implements DraggableObject<W>, DroppableObject<W> {
	DragStartEndHandlerWrapper dragStartEndHandlerWrapper;
	DropEventsHandlerWrapper dropEventsHandlerWrapper;
	DraggableWidget<W> dragWidget;
	DroppableWidget<W> dropWidget;
	private boolean disabled;

	@Inject
	public EmulatedDragDrop(@Assisted("widget") W widget, @Assisted("type") DragDropType type, @Assisted("disableAutoBehavior") boolean disableAutoBehavior) {
		if (type == DragDropType.DRAG) {
			createDrag(widget);
		} else {
			createDrop(widget);
		}
	}

	private void createDrag(W widget) {
		DraggableOptions options = new DraggableOptions();
		options.setHelper(HelperType.CLONE);
		options.setRevert(RevertOption.ON_INVALID_DROP);
		options.setCursor(Cursor.MOVE);
		dragWidget = new DraggableWidget<W>(widget, options);
		dragWidget.setDraggableOptions(options);
		dragWidget.setDraggingOpacity(.8f);
	}

	private void createDrop(W widget) {
		dropWidget = new DroppableWidget<W>(widget);
	}

	public DragStartEndHandlerWrapper getDragStartEndHandlerWrapper() {
		if (dragStartEndHandlerWrapper == null) {
			dragStartEndHandlerWrapper = new DragStartEndHandlerWrapper(dragWidget);
		}
		return dragStartEndHandlerWrapper;
	}

	public DropEventsHandlerWrapper getDropEventsHandlerWrapper() {
		if (dropEventsHandlerWrapper == null) {
			dropEventsHandlerWrapper = new DropEventsHandlerWrapper(dropWidget);
		}
		return dropEventsHandlerWrapper;
	}

	@Override
	public HandlerRegistration addDragEndHandler(DragEndHandler handler) {
		DragStartEndHandlerWrapper wrapper = getDragStartEndHandlerWrapper();
		return wrapper.wrap(handler);
	}

	@Override
	public HandlerRegistration addDragEnterHandler(DragEnterHandler handler) {
		return getDropEventsHandlerWrapper().wrap(handler);
	}

	@Override
	public HandlerRegistration addDragLeaveHandler(DragLeaveHandler handler) {
		return getDropEventsHandlerWrapper().wrap(handler);
	}

	@Override
	public HandlerRegistration addDragOverHandler(DragOverHandler handler) {
		return getDropEventsHandlerWrapper().wrap(handler);
	}

	@Override
	public HandlerRegistration addDragStartHandler(DragStartHandler handler) {
		DragStartEndHandlerWrapper wrapper = getDragStartEndHandlerWrapper();
		return wrapper.wrap(handler);
	}

	@Override
	public HandlerRegistration addDropHandler(DropHandler handler) {
		return getDropEventsHandlerWrapper().wrap(handler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public W getDraggableWidget() {
		return (W) dragWidget;
	}

	@SuppressWarnings("unchecked")
	@Override
	public W getDroppableWidget() {
		return  (W) dropWidget;
	}

	@Override
	public void setDisableDrag(boolean disable) {
		this.disabled = disable;

	}

	@Override
	public void setDisableDrop(boolean disable) {
		this.disabled = disable;

	}

}
