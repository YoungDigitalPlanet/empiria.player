package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventHandler;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public abstract class AbstractDragDrop<W extends Widget> {
	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected OverlayTypesParser overlayTypesParser;

	@Inject
	protected PageScopeFactory scopeFactory;

	@Inject
	protected StyleNameConstants styleNames;


	protected boolean valueChangeSelfFire = false;

	protected void fireEvent(DragDropEventTypes type, DragDataObject dataObject) {
		if (type != null && dataObject != null) {
			DragDropEvent dragDropEvent = new DragDropEvent(type, getIModule());
			dragDropEvent.setIModule(getIModule());
			dragDropEvent.setDragDataObject(dataObject);
			eventsBus.fireEventFromSource(dragDropEvent, getIModule(), scopeFactory.getCurrentPageScope());
		}
	}

	protected void registerDropZone() {
		DragDropEvent event = new DragDropEvent(DragDropEventTypes.REGISTER_DROP_ZONE, getIModule());
		event.setIModule(getIModule());
		eventsBus.fireEventFromSource(event, getIModule(), scopeFactory.getCurrentPageScope());

	}

	protected void addHandlerDisableEnableEvent(){
		eventsBus.addHandlerToSource(DragDropEvent.getType(DragDropEventTypes.DISABLE_DROP_ZONE), getIModule(), new DragDropEventHandler() {
			@Override
			public void onDragEvent(DragDropEvent event) {
				setDisableDrop(true);
			}
		});

		eventsBus.addHandler(DragDropEvent.getType(DragDropEventTypes.ENABLE_ALL_DROP_ZONE), new DragDropEventHandler() {
			@Override
			public void onDragEvent(DragDropEvent event) {
				setDisableDrop(false);
			}
		});

	}
	public HasValueChangeHandlers<?> findHasValueChangeHandlers(Widget widget) {
		HasValueChangeHandlers<?> returnValue = null;
		if (widget instanceof HasValueChangeHandlers) {
			returnValue = (HasValueChangeHandlers<?>) widget;
		} else {
			if (widget instanceof HasWidgets) {
				HasWidgets hasWidgets = (HasWidgets) widget;
				for (Widget wid : hasWidgets) {
					returnValue = findHasValueChangeHandlers(wid);
					if (returnValue != null) {
						break;
					}
				}
			}
		}
		return returnValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setAutoBehaviorForDrop(boolean disableAutoBehavior) {
		registerDropZone();
		addHandlerDisableEnableEvent();
		HasValueChangeHandlers widget = findHasValueChangeHandlers(getOriginalWidget());
		if (widget != null) {
			widget.addValueChangeHandler(new ValueChangeHandler() {
				@Override
				public void onValueChange(ValueChangeEvent event) {
					if (valueChangeSelfFire) {
						valueChangeSelfFire = false;
					} else {
						String value = String.valueOf(event.getValue());
						NativeDragDataObject object = overlayTypesParser.get();
						object.setValue(value);
						fireEvent(DragDropEventTypes.USER_CHANGE_DROP_ZONE, object);
					}
				}
			});
		}
	}

	/**
	 * Szuka w hierarchi widgetu posiadajacego wartosc
	 *
	 * @param widget
	 * @return
	 */
	public HasValue<?> findHasValue(Widget widget) {
		HasValue<?> returnValue = null;
		if (widget instanceof HasValue) {
			returnValue = (HasValue<?>) widget;
		} else {
			if (widget instanceof HasWidgets) {
				HasWidgets hasWidgets = (HasWidgets) widget;
				for (Widget wid : hasWidgets) {
					returnValue = findHasValue(wid);
					if (returnValue != null) {
						break;
					}
				}
			}
		}
		return returnValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected String putValue(String jsonObject) {
		String value = null;
		HasValue widget = findHasValue(getOriginalWidget());
		if (widget != null && overlayTypesParser.isValidJSON(jsonObject)) {
			NativeDragDataObject dragData = overlayTypesParser.get(jsonObject);
			value = dragData.getValue();
			if (!widget.getValue().equals(value)) {
				valueChangeSelfFire = true;
				widget.setValue(value, true);
				fireEvent(DragDropEventTypes.DRAG_END, dragData);
			}
		}
		return value;
	}

	protected void addStyleForWidget(String style,boolean disabled) {
		if (!disabled) {
			getOriginalWidget().addStyleName(style);
		}
	}

	protected void removeStyleForWidget(String style,boolean disabled) {
		if (!disabled) {
			getOriginalWidget().removeStyleName(style);
		}
	}

	protected abstract W getOriginalWidget();

	protected abstract IModule getIModule();

	protected abstract void setDisableDrop(boolean disabled);
}
