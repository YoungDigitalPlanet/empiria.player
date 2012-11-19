package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public abstract class AbstractDragDrop {
	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected OverlayTypesParser overlayTypesParser;

	@Inject
	protected PageScopeFactory scopeFactory;

	protected boolean valueChangeSelfFire = false;

	protected void fireEvent(DragDropEventTypes type, DragDataObject dataObject) {
		if (type != null && dataObject != null) {
			DragDropEvent dragDropEvent = new DragDropEvent(type, getIModule());
			dragDropEvent.setDragDataObject(dataObject);
			eventsBus.fireEventFromSource(dragDropEvent, getIModule(), scopeFactory.getCurrentPageScope());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setAutoBehaviorForDrop(boolean disableAutoBehavior) {
		Widget widget = getWidget();
		if (widget instanceof HasValueChangeHandlers) {
			((HasValueChangeHandlers) widget).addValueChangeHandler(new ValueChangeHandler() {
				@Override
				public void onValueChange(ValueChangeEvent event) {
					if (valueChangeSelfFire) {
						valueChangeSelfFire = false;
					} else {
						Object source = event.getSource();
						String value = String.valueOf(event.getValue());
						NativeDragDataObject object = overlayTypesParser.get();
						object.setValue(value);
						fireEvent(DragDropEventTypes.DRAG_END, object);
					}
				}
			});
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected String putValue(String jsonObject) {
		String value = null;
		Widget widget = getWidget();
		if (widget instanceof HasValue && overlayTypesParser.isValidJSON(jsonObject)) {
			NativeDragDataObject dragData = overlayTypesParser.get(jsonObject);
			value = dragData.getValue();
			if (!((HasValue) widget).getValue().equals(value)) {
				valueChangeSelfFire = true;
				((HasValue) widget).setValue(value, true);
				fireEvent(DragDropEventTypes.DRAG_END, dragData);
			}
		}
		return value;
	}

	protected abstract Widget getWidget();

	protected abstract IModule getIModule();
}
