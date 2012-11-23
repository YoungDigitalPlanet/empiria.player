package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.events.DragStopEvent;
import gwtquery.plugins.draggable.client.events.DragStopEvent.DragStopEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Wrapper dla com.google.gwt.event.dom.client.DragStartEvent oraz
 * com.google.gwt.event.dom.client.DragEndEvent
 *
 * @author plelakowski
 *
 */
public class DragStartEndHandlerWrapper implements DragDropSetGetData {
	protected static class DragStartEventWrapper extends com.google.gwt.event.dom.client.DragStartEvent {
		private final DragDropSetGetData wrapper;

		public DragStartEventWrapper(DragDropSetGetData wrapper) {
			this.wrapper = wrapper;
		}

		@Override
		public void setData(String format, String data) {
			wrapper.setData(format, data);
		}

		@Override
		public String getData(String format) {
			return wrapper.getData(format);
		}
	}

	protected static class DragEndEventWrapper extends com.google.gwt.event.dom.client.DragEndEvent {
		private final DragDropSetGetData wrapper;

		public DragEndEventWrapper(DragDropSetGetData wrapper) {
			this.wrapper = wrapper;
		}

		@Override
		public void setData(String format, String data) {
			wrapper.setData(format, data);
		}

		@Override
		public String getData(String format) {
			return wrapper.getData(format);
		}
	}

	private final DraggableWidget<?> draggableWidget;

	private final Map<String, String> data = new HashMap<String, String>();

	public DragStartEndHandlerWrapper(DraggableWidget<?> draggableWidget) {
		this.draggableWidget = draggableWidget;
	}

	@Override
	public void setData(String format, String data) {
		this.data.put(format, data);
	}

	@Override
	public String getData(String format) {
		return this.data.get(format);
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