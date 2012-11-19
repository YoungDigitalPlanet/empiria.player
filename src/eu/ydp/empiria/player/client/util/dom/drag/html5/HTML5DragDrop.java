package eu.ydp.empiria.player.client.util.dom.drag.html5;

import static eu.ydp.empiria.player.client.util.dom.drag.DragDropType.DRAG;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.dom.drag.AbstractDragDrop;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropType;
import eu.ydp.empiria.player.client.util.dom.drag.DraggableObject;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;

public class HTML5DragDrop<W extends Widget> extends AbstractDragDrop implements DraggableObject<W>, DroppableObject<W> {

	private final W widget;
	private final boolean disableAutoBehavior;

	@Inject
	protected StyleNameConstants styleNames;

	private boolean disabled = false;
	private final IModule imodule;

	@Inject
	public HTML5DragDrop(final @Assisted("widget") W widget, @Assisted("imodule") IModule imodule, @Assisted("type") DragDropType type, @Assisted("disableAutoBehavior") boolean disableAutoBehavior) {
		this.widget = widget;
		this.imodule = imodule;
		this.disableAutoBehavior = disableAutoBehavior;
		if (type == DRAG) {
			setAutoBehaviorForDrag(disableAutoBehavior);
		} else {
			setAutoBehaviorForDrop(disableAutoBehavior);
		}
	}

	protected final native void enableDragForIE(Element element)/*-{
		element.dragDrop();
	}-*/;

	private void addStyleForWidget(String style) {
		if (!disabled) {
			widget.addStyleName(style);
		}
	}

	private void removeStyleForWidget(String style) {
		if (!disabled) {
			widget.removeStyleName(style);
		}
	}

	@Override
	protected void setAutoBehaviorForDrop(boolean disableAutoBehavior) {
		if (!disableAutoBehavior) {
			widget.addDomHandler(new DropHandler() {
				@Override
				public void onDrop(DropEvent event) {
					removeStyleForWidget(styleNames.QP_DROPZONE_OVER());
					if (!disabled) {
						putValue(event.getData("json"));
					}
					event.stopPropagation();
					event.preventDefault();
				}
			}, DropEvent.getType());

			widget.addDomHandler(new DragEnterHandler() {

				@Override
				public void onDragEnter(DragEnterEvent event) {
					setDropEffect(event.getDataTransfer(), (disabled) ? "none" : "move");
					event.stopPropagation();
					event.preventDefault();
				}
			}, DragEnterEvent.getType());

			widget.addDomHandler(new DragOverHandler() {
				@Override
				public void onDragOver(DragOverEvent event) {
					addStyleForWidget(styleNames.QP_DROPZONE_OVER());
					setDropEffect(event.getDataTransfer(), (disabled) ? "none" : "move");
					event.stopPropagation();
					event.preventDefault();
				}
			}, DragOverEvent.getType());

			widget.addDomHandler(new DragLeaveHandler() {
				@Override
				public void onDragLeave(DragLeaveEvent event) {
					removeStyleForWidget(styleNames.QP_DROPZONE_OVER());
				}
			}, DragLeaveEvent.getType());
			super.setAutoBehaviorForDrop(disableAutoBehavior);
		}
	}

	private native void setDropEffect(JavaScriptObject dataTransferObject, String value)/*-{
		dataTransferObject.dropEffect = value;
		//	dataTransferObject.effectAllowed=value;
	}-*/;

	private void setAutoBehaviorForDrag(boolean disableAutoBehavior) {
		// IE bug
		if (UserAgentChecker.isUserAgent(UserAgent.IE9, UserAgent.IE8)) {
			widget.addDomHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					if (!disabled) {
						enableDragForIE(widget.getElement());
					}
				}
			}, MouseDownEvent.getType());
		}
//		widget.addDomHandler(new DragStartHandler() {
//
//			@Override
//			public void onDragStart(DragStartEvent event) {
//				// setEffectAllowed(event.getDataTransfer(),"move");
//			}
//		}, DragStartEvent.getType());

		widget.getElement().setDraggable(Element.DRAGGABLE_TRUE);
	}

	@Override
	public HandlerRegistration addDragEndHandler(DragEndHandler handler) {
		return widget.addDomHandler(handler, DragEndEvent.getType());
	}

	@Override
	public HandlerRegistration addDragEnterHandler(DragEnterHandler handler) {
		return widget.addDomHandler(handler, DragEnterEvent.getType());
	}

	@Override
	public HandlerRegistration addDragLeaveHandler(DragLeaveHandler handler) {
		return widget.addDomHandler(handler, DragLeaveEvent.getType());
	}

	@Override
	public HandlerRegistration addDragOverHandler(DragOverHandler handler) {
		return widget.addDomHandler(handler, DragOverEvent.getType());
	}

	@Override
	public HandlerRegistration addDragStartHandler(DragStartHandler handler) {
		return widget.addDomHandler(handler, DragStartEvent.getType());
	}

	@Override
	public HandlerRegistration addDropHandler(DropHandler handler) {
		return widget.addDomHandler(handler, DropEvent.getType());
	}

	@Override
	public W getDraggableWidget() {
		return widget;
	}

	@Override
	public W getDroppableWidget() {
		return widget;
	}

	@Override
	protected Widget getWidget() {
		return widget;
	}

	@Override
	protected IModule getIModule() {
		return imodule;
	}

	@Override
	public void setDisableDrag(boolean disable) {
		this.disabled = disable;
		if (disable) {
			widget.getElement().removeAttribute("draggable");
		} else {
			widget.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		}
	}

	@Override
	public void setDisableDrop(boolean disable) {
		this.disabled = disable;
	}

}
