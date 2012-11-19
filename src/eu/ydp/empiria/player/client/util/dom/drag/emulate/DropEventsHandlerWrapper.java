package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class DropEventsHandlerWrapper implements DragDropSetGetData  {
	private final Map<String, String> data = new HashMap<String, String>();

	protected class DragEnterEventWrapper extends DragEnterEvent{
		private final DragDropSetGetData setGetData;

		public DragEnterEventWrapper(DragDropSetGetData data) {
			this.setGetData = data;
		}
		@Override
		public void setData(String format, String data) {
			setGetData.setData(format, data);
		}

		@Override
		public String getData(String format) {
			return setGetData.getData(format);
		}
	}

	protected class DragLeaveEventWrapper extends DragLeaveEvent{
		private final DragDropSetGetData setGetData;

		public DragLeaveEventWrapper(DragDropSetGetData data) {
			this.setGetData = data;
		}
		@Override
		public void setData(String format, String data) {
			setGetData.setData(format, data);
		}

		@Override
		public String getData(String format) {
			return setGetData.getData(format);
		}
	}

	protected class DragOverEventWrapper extends DragOverEvent{
		private final DragDropSetGetData setGetData;

		public DragOverEventWrapper(DragDropSetGetData data) {
			this.setGetData = data;
		}
		@Override
		public void setData(String format, String data) {
			setGetData.setData(format, data);
		}

		@Override
		public String getData(String format) {
			return setGetData.getData(format);
		}
	}

	protected class DropEventWrapper extends com.google.gwt.event.dom.client.DropEvent{
		private final DragDropSetGetData setGetData;

		public DropEventWrapper(DragDropSetGetData data) {
			this.setGetData = data;
		}
		@Override
		public void setData(String format, String data) {
			setGetData.setData(format, data);
		}

		@Override
		public String getData(String format) {
			return setGetData.getData(format);
		}
	}


	private final DroppableWidget<?> droppableWidget;

	public DropEventsHandlerWrapper(DroppableWidget<?> droppableWidget) {
		this.droppableWidget = droppableWidget;
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
				dropHandler.onDrop(new DropEventWrapper(DropEventsHandlerWrapper.this));
			}
		});
	}
	@Override
	public void setData(String format, String data) {
		this.data.put(format, data);
	}

	@Override
	public String getData(String format) {
		return this.data.get(format);
	}
}
