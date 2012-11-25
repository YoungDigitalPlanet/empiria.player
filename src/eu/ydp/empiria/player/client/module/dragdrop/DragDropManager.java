package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class DragDropManager implements Extension, DragDropEventHandler, PlayerEventHandler {

	@Inject
	EventsBus eventsBus;

	@Inject
	PageScopeFactory scopeFactory;
	
	Multimap<IModule, SourceListModule> dropZones = ArrayListMultimap.create(); 
	
	Map<IModule, String> gapValuesCache = new HashMap<IModule, String>();
	Map<IModule, SourceListModule> gapValueSourceCache = new HashMap<IModule, SourceListModule>();
	
	SourceListModule lastDragSource;
	//IModule lastGapChanged;

	List<IModule> waitingForRegister = new ArrayList<IModule>();

	DragDropManagerHelper helper;
		
	@Override
	public ExtensionType getType() {
		return ExtensionType.MULTITYPE;
	}

	@Override
	public void init() {
		this.helper = new DragDropManagerHelper(eventsBus, scopeFactory);
		eventsBus.addHandler(DragDropEvent.getType(DragDropEventTypes.REGISTER_DROP_ZONE), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_LOADED), this);
		//eventsBus.addHandler(DragDropEvent.getType(DragDropEventTypes.VALUE_FOUND_IN_SOURCELIST), this);
		//eventsBus.addHandler(DragDropEvent.getType(DragDropEventTypes.VALUE_NOT_FOUND_IN_SOURCELIST), this);
	}
	
	private void handleDragStart(DragDropEvent event) {
		lastDragSource = (SourceListModule) event.getIModule();		
		eventsBus.fireEvent(new DragDropEvent(DragDropEventTypes.ENABLE_ALL_DROP_ZONE, null), scopeFactory.getCurrentPageScope());
		
		for (IModule gap : findInapplicableGaps(lastDragSource)) {
			DragDropEvent gapDisableEvent = new DragDropEvent(DragDropEventTypes.DISABLE_DROP_ZONE, gap);			
			gapDisableEvent.setIModule(gap);			
			eventsBus.fireEventFromSource(gapDisableEvent, gap, scopeFactory.getCurrentPageScope());			
		}		
	}
	
	List<IModule> findInapplicableGaps(SourceListModule dragSource) {
		List<IModule> list = new ArrayList<IModule>();
		
		for (IModule gap : dropZones.keySet()) {
			if (!dropZones.containsEntry(gap, dragSource)) {
				list.add(gap);
			}
		}

		return list;
	}
	
	private void handleDragEnd(DragDropEvent event) {
		IModule gapModule = (IModule)event.getSource();		
		updateDragDataObject(event, gapModule);		
		String newValue = event.getDragDataObject().getValue();
		gapValuesCache.put(gapModule, newValue);
		
		if (lastDragSource != null) {
			gapValueSourceCache.put(gapModule, lastDragSource); // FIXME: is it needed?
			helper.fireEventFromSource(lastDragSource, event.getDragDataObject(), DragDropEventTypes.DRAG_END, gapModule);
		}
	}

	private void handleUserChangedDropZone(DragDropEvent event) {
		//lastGapChanged = (IModule)event.getSource();		
		
		IModule gapChanged = (IModule)event.getSource(); // FIXME: why IModule is not set? what is difference between source and iModule?	
		
		event.setIModule(gapChanged);
		updateDragDataObject(event, gapChanged);
		String newValue = event.getDragDataObject().getValue();
		gapValuesCache.put(gapChanged, newValue);
		
		Collection<SourceListModule> assignedSourceLists = dropZones.get(gapChanged);		
		for (SourceListModule sourceList : assignedSourceLists) {			
			if (sourceList.containsValue(newValue)) {
				helper.fireEventFromSource(sourceList, event.getDragDataObject(), DragDropEventTypes.DRAG_END, gapChanged);
				break;
			}				
			//helper.fireEventFromSource(sourceList, event.getDragDataObject(), DragDropEventTypes.FIND_VALUE_IN_SOURCELIST);
		}		
	}
	
	private void updateDragDataObject(DragDropEvent event, IModule gapModule) {				
		String previousValue = gapValuesCache.get(gapModule);
		event.getDragDataObject().setPreviousValue(previousValue);		
	}	
	
	@Override
	public void onDragEvent(DragDropEvent event) {
		switch (event.getType()) {
		case DRAG_START:
			handleDragStart(event);
			break;
		case USER_CHANGE_DROP_ZONE:
			handleUserChangedDropZone(event);
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
		eventsBus.addHandlerToSource(DragDropEvent.getType(DragDropEventTypes.USER_CHANGE_DROP_ZONE), event.getIModule(), this);
		waitingForRegister.add(event.getIModule()); 
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

	@Override
	public void onPlayerEvent(PlayerEvent event) {		
		for (IModule dropZone : waitingForRegister) {
			buildDropZoneSourceListRelationships(dropZone, dropZone.getParentModule());			
		}		
	}
		
}