package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.JavaScriptObject;
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

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.AbstractDragDrop;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropType;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.debug.logger.Debug;
import gwtquery.plugins.draggable.client.DraggableOptions;
import gwtquery.plugins.draggable.client.DraggableOptions.HelperType;
import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

/**
 * For mobile and old browsers gQuery used
 *
 */
public class EmulatedDragDrop<W extends Widget> extends AbstractDragDrop<W> implements DraggableObject<W>, DroppableObject<W> {
	private static final String JSON = "json";
	private static final String DATA_JSON = "data-json";
	DragStartEndHandlerWrapper dragStartEndHandlerWrapper;
	DropEventsHandlerWrapper dropEventsHandlerWrapper;
	DraggableWidget<W> dragWidget;
	DroppableWidget<W> dropWidget;
	private boolean disabled;
	private final W originalWidget;
	private final boolean disableAutoBehavior;
	private final IModule imodule;
	private final DragDropType type;

	@Inject
	private OverlayTypesParser parser;

	@Inject
	public EmulatedDragDrop(@Assisted("widget") W widget, @Assisted("imodule") IModule imodule, @Assisted("type") DragDropType type,
			@Assisted("disableAutoBehavior") boolean disableAutoBehavior) {
		this.originalWidget = widget;
		this.imodule = imodule;
		this.disableAutoBehavior = disableAutoBehavior;
		this.type = type;
		Debug.log("emulated Drop");
	}

	@PostConstruct
	public void postConstruct() {
		Debug.log("emulated Drop post construct");
		if (type == DragDropType.DRAG) {
			createDrag(originalWidget);
		} else {
			createDrop(originalWidget);
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
		dragWidget.addDragStartHandler(new DragStartEventHandler() {

			@Override
			public void onDragStart(DragStartEvent event) {
				Debug.log("DRAG START");
			}
		});


	}

	private native void ss(JavaScriptObject ob)/*-{
		console.log(ob);
	}-*/;
	private void createDrop(W widget) {
		dropWidget = new DroppableWidget<W>(widget);
		dropWidget.addDropHandler(new DropEventHandler() {
			@Override
			public void onDrop(DropEvent event) {
				ss(event.getDraggable());
				Debug.log("drop "+event.getDraggable());
				Debug.log("drop "+event.getDraggable().getAttribute(DATA_JSON));
				JsonAttr jsonAttr = overlayTypesParser.get(event.getDraggable().getAttribute(DATA_JSON));
				getDropEventsHandlerWrapper().setJsonAttr(jsonAttr);
			}
		});

		if (!disableAutoBehavior) {
			setAutoBehaviorForDrop(disableAutoBehavior);
		}
		super.setAutoBehaviorForDrop(disableAutoBehavior);
	}

	@Override
	protected void setAutoBehaviorForDrop(boolean disableAutoBehavior) {
		dropWidget.addDropHandler(new DropEventHandler() {
			@Override
			public void onDrop(DropEvent event) {
				JsonAttr jsonAttr = overlayTypesParser.get(event.getDraggable().getAttribute(DATA_JSON));
				Debug.log("json attr"+jsonAttr.toJSON()+" "+jsonAttr.getAttrValue(DATA_JSON));
				if (jsonAttr.getAttrValue(JSON) != null) {
					putValue(jsonAttr.getAttrValue(JSON));
				}
			}
		});
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
		return getDragStartEndHandlerWrapper().wrap(handler);
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
		Debug.log("add drag start statata "+handler);
		return getDragStartEndHandlerWrapper().wrap(handler);
	}

	@Override
	public HandlerRegistration addDropHandler(DropHandler handler) {
		return getDropEventsHandlerWrapper().wrap(handler);
	}

	@Override
	public Widget getDraggableWidget() {
		return dragWidget;
	}

	@Override
	public Widget getDroppableWidget() {
		return dropWidget;
	}

	@Override
	public W getOriginalWidget() {
		return originalWidget;
	}

	@Override
	public void setDisableDrag(boolean disable) {
		this.disabled = disable;

	}

	@Override
	public void setDisableDrop(boolean disable) {
		dropWidget.setDisabled(disable);
		this.disabled = disable;

	}

	@Override
	protected IModule getIModule() {
		return imodule;
	}

}
