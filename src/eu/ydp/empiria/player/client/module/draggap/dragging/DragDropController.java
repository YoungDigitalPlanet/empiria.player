package eu.ydp.empiria.player.client.module.draggap.dragging;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleFactory;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.Wrapper;

public class DragDropController {

	private final DragGapView dragGapView;
	private final DragGapModuleFactory dragGapModuleFactory;
	private DropZoneGuardian dropZoneGuardian;

	@Inject
	public DragDropController(
			@ModuleScoped DragGapView dragGapView, 
			DragGapModuleFactory dragGapModuleFactory) {
		this.dragGapView = dragGapView;
		this.dragGapModuleFactory = dragGapModuleFactory;
	}
	
	public void initializeDrop(String moduleIdentifier) {
		DroppableObject<FlowPanelWithDropZone> droppable = dragGapView.enableDropCapabilities();
		SourceListConnectedDropHandler dropHandler = dragGapModuleFactory.createSourceListConnectedDropHandler(moduleIdentifier);
		droppable.addDropHandler(dropHandler);
		dropZoneGuardian = dragGapModuleFactory.createDropZoneGuardian(droppable, droppable.getDroppableWidget());
	}

	public void initializeDrag(String moduleIdentifier, Wrapper<String> itemIdWrapper) {
		SourceListConnectedDragHandler dragHandler = dragGapModuleFactory.createSourceListConnectedDragHandler(moduleIdentifier, itemIdWrapper);
		dragGapView.setDragStartHandler(dragHandler);
		dragGapView.setDragEndHandler(dragHandler);
	}

	public void lockDropZone() {
		dropZoneGuardian.lockDropZone();
	}

	public void unlockDropZone() {
		dropZoneGuardian.unlockDropZone();
	}
}
