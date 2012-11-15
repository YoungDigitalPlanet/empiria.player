package eu.ydp.empiria.player.client.util.events.dragdrop;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class DragDropEvent extends AbstractEvent<DragDropEventHandler, DragDropEventTypes> {
	public static EventTypes<DragDropEventHandler, DragDropEventTypes> types = new EventTypes<DragDropEventHandler, DragDropEventTypes>();

	DragDataObject dragDataObject;

	private IModule module;

	public DragDropEvent(DragDropEventTypes type, Object source) {
		super(type, source);
	}

	@Override
	protected EventTypes<DragDropEventHandler, DragDropEventTypes> getTypes() {
		return types;
	}

	@Override
	public void dispatch(DragDropEventHandler handler) {
		handler.onDragEvent(this);
	}

	public void setIModule(IModule module){
		this.module = module;
	}

	public IModule getModule() {
		return module;
	}

	public void setDragDataObject(DragDataObject dragDataObject) {
		this.dragDataObject = dragDataObject;
	}

	public DragDataObject getDragDataObject() {
		return dragDataObject;
	}

	public static Type<DragDropEventHandler, DragDropEventTypes> getType(DragDropEventTypes type) {
		return types.getType(type);
	}

	public static Type<DragDropEventHandler, DragDropEventTypes>[] getTypes(DragDropEventTypes... type) {
		return types.getTypes(type);
	}

}
