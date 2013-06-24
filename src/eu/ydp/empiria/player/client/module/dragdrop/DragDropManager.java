package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventHandler;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class DragDropManager extends InternalExtension implements DragDropEventHandler, PlayerEventHandler {

	@Inject
	EventsBus eventsBus;

	@Inject
	PageScopeFactory scopeFactory;

	Multimap<IModule, SourceListModule> dropZones = ArrayListMultimap.create();

	Map<IModule, String> gapValuesCache = new HashMap<IModule, String>();
	Map<IModule, SourceListModule> gapValueSourceCache = new HashMap<IModule, SourceListModule>();

	SourceListModule lastDragSource;

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
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_INITIALIZED), this);
	}

	private void handleDragStart(DragDropEvent event) {
		lastDragSource = (SourceListModule) event.getIModule();
		eventsBus.fireEvent(new DragDropEvent(DragDropEventTypes.ENABLE_ALL_DROP_ZONE, null), scopeFactory.getCurrentPageScope());

		for (IModule gap : findInapplicableGaps(lastDragSource)) {
			helper.fireEventFromSource(gap, event.getDragDataObject(), DragDropEventTypes.DISABLE_DROP_ZONE);
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

	private void processGapChanged(DragDropEvent event) {
		IModule gapModule = event.getIModule();
		DragDataObject dragDataObject = event.getDragDataObject();
//	//	String newValue = dragDataObject.getValue();
//
//		undoLastChange(event, gapModule);
//
////		dragDataObject.setValue(newValue);
//	//	dragDataObject.setPreviousValue(null);
//		if (DragDropEventTypes.DRAG_END.equals(event.getType())) {
//			processGapChangedOnDragEnd(gapModule, event.getDragDataObject(), newValue);
//		} else if (DragDropEventTypes.USER_CHANGE_DROP_ZONE.equals(event.getType())) {
//			processGapChangedOnManualUserChange(event.getDragDataObject(), gapModule, newValue);
//		}
	}

	private void undoLastChange(DragDropEvent event, IModule gapModule) {
		SourceListModule associatedSourceList = gapValueSourceCache.get(gapModule);
		if (associatedSourceList != null) {
			DragDataObject dragDataObject = event.getDragDataObject();
		//	dragDataObject.setValue(null);
		//	dragDataObject.setPreviousValue(gapValuesCache.get(gapModule));
			helper.fireEventFromSource(associatedSourceList, dragDataObject, DragDropEventTypes.DRAG_END, gapModule);
			gapValueSourceCache.remove(gapModule);
		}
	}

	private void processGapChangedOnDragEnd(IModule gapModule, DragDataObject dragDataObject, String newValue) {
		if (lastDragSource != null) {
			storeUndoData(gapModule, newValue, lastDragSource);
			helper.fireEventFromSource(lastDragSource, dragDataObject, DragDropEventTypes.DRAG_END, gapModule);
		}
	}

	private void processGapChangedOnManualUserChange(DragDataObject dragDataObject, IModule gapModule, String newValue) {
		Collection<SourceListModule> assignedSourceLists = dropZones.get(gapModule);
		for (SourceListModule sourceList : assignedSourceLists) {
//			if (sourceList.containsValue(newValue)) {
//				storeUndoData(gapModule, newValue, sourceList);
//				helper.fireEventFromSource(sourceList, dragDataObject, DragDropEventTypes.DRAG_END, gapModule);
//				break;
//			}
		}
	}

	private void storeUndoData(IModule gapModule, String newValue, SourceListModule sourceList) {
		gapValueSourceCache.put(gapModule, sourceList);
		gapValuesCache.put(gapModule, newValue);
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

	@Override
	public void onDragEvent(DragDropEvent event) {
		switch (event.getType()) {
		case DRAG_START:
			handleDragStart(event);
			break;
		case USER_CHANGE_DROP_ZONE:
		case DRAG_END:
			processGapChanged(event);
			break;
		case REGISTER_DROP_ZONE:
			registerDropZone(event);
			break;
		default:
			break;
		}
	}
}