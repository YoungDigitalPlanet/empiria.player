package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventHandler;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public class DragDropManager implements Extension, DragDropEventHandler {

	@Inject
	EventsBus eventsBus;

	@Inject
	PageScopeFactory scopeFactory;
	
	Multimap<IModule, SourceListModule> dropZones = ArrayListMultimap.create(); 
	
	HashMap<IModule, String> moduleValues = new HashMap<IModule, String>();
	
	SourceListModule dragSource;
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.MULTITYPE;
	}

	@Override
	public void init() {
		eventsBus.addHandler(DragDropEvent.getType(DragDropEventTypes.REGISTER_DROP_ZONE), this);
	}

	private void handleDragCancell(DragDropEvent event) {
		eventsBus.fireEventFromSource(event, event.getIModule(), scopeFactory.getCurrentPageScope());
	}
	
	private void handleDragStart(DragDropEvent event) {
		dragSource = (SourceListModule) event.getIModule();
		
		for (IModule gap : findInapplicableGaps(dragSource)) {
			DragDropEvent gapDisableEvent = new DragDropEvent(DragDropEventTypes.DISABLE_DROP_ZONE, gap);
			gapDisableEvent.setIModule(gap);
			eventsBus.fireEventFromSource(gapDisableEvent, gap, scopeFactory.getCurrentPageScope());			
		}		
	}

	Vector<IModule> findInapplicableGaps(SourceListModule dragSource) {
		Vector<IModule> list = new Vector<IModule>();
		
		for (IModule gap : dropZones.keySet()) {
			if (!dropZones.containsEntry(gap, dragSource)) {
				list.add(gap);
			}
		}

		return list;
	}
	
	private void handleDragEnd(DragDropEvent event) {
		IModule module = event.getIModule();
		
		String newValue = event.getDragDataObject().getValue();		
		String previousValue = moduleValues.get(module);		
		moduleValues.put(module, newValue);
		event.getDragDataObject().setPreviousValue(previousValue);
		
		if (dragSource != null) {
			eventsBus.fireEventFromSource(event, dragSource, scopeFactory.getCurrentPageScope());
		}
	}	
	
	@Override
	public void onDragEvent(DragDropEvent event) {
		switch (event.getType()) {
		case DRAG_START:
			handleDragStart(event);
			break;
		case DRAG_CANCELL:
			handleDragCancell(event);
			break;
		case DRAG_END:
			handleDragEnd(event);
			break;
		case REGISTER_DROP_ZONE:
			registerDropZone(event);
			break;
		default:
			break;
		}
	}
	
	protected void registerDropZone(DragDropEvent event) {		
		eventsBus.addHandlerToSource(DragDropEvent.getType(DragDropEventTypes.DRAG_END), event.getIModule(), this);		
		IModule dropZone = event.getIModule();				
		buildDropZoneSourceListRelationships(dropZone, dropZone.getParentModule());
	}

	private void buildDropZoneSourceListRelationships(IModule dropZone, HasChildren module) {
		if (module != null) {
			List<IModule> relatedModules = module.getChildrenModules();
			for (IModule relatedModule : relatedModules) {
				if (relatedModule instanceof SourceListModule) {										
					dropZones.put(dropZone, (SourceListModule)relatedModule);					
					eventsBus.addHandlerToSource(DragDropEvent.getType(DragDropEventTypes.DRAG_START), relatedModule, this);
				}
			}
			buildDropZoneSourceListRelationships(dropZone, module.getParentModule());
		}
	}		
}