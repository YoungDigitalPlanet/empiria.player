package eu.ydp.empiria.player.client.module.dragdrop;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public class DragDropManagerHelper {

	private EventsBus eventsBus;
	private PageScopeFactory scopeFactory;

	public DragDropManagerHelper(EventsBus eventsBus, PageScopeFactory scopeFactory) {
		this.eventsBus = eventsBus;
		this.scopeFactory = scopeFactory;
	}
	
	public void fireEventFromSource(IModule source, DragDataObject dragDropDataObject, DragDropEventTypes eventType, IModule correspondingModule) {
		if (correspondingModule == null) {
			correspondingModule = source;
		}		
		DragDropEvent event = new DragDropEvent(eventType, source);
		event.setDragDataObject(dragDropDataObject);
		event.setIModule(correspondingModule);		
		eventsBus.fireEventFromSource(event, source, scopeFactory.getCurrentPageScope());
	}
	
	public void fireEventFromSource(IModule source, DragDataObject dragDropDataObject, DragDropEventTypes eventType) {
		fireEventFromSource(source, dragDropDataObject, eventType, null);
	}
}
