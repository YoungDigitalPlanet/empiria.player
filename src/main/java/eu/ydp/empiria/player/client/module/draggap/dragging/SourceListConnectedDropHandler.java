package eu.ydp.empiria.player.client.module.draggap.dragging;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SourceListConnectedDropHandler implements DropHandler {

	private final DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor;
	private final SourceListManagerAdapter sourceListManagerAdapter;

	@Inject
	public SourceListConnectedDropHandler(@ModuleScoped SourceListManagerAdapter sourceListManagerAdapter,
			DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor) {
		this.sourceListManagerAdapter = sourceListManagerAdapter;
		this.dragDataObjectFromEventExtractor = dragDataObjectFromEventExtractor;
	}

	@Override
	public void onDrop(DropEvent event) {
		Optional<DragDataObject> objectFromEvent = dragDataObjectFromEventExtractor.extractDroppedObjectFromEvent(event);
		if (objectFromEvent.isPresent()) {
			informSourceListManagerAboutDrop(objectFromEvent.get());
		}
	}

	private void informSourceListManagerAboutDrop(DragDataObject dragDataObject) {
		String itemID = dragDataObject.getItemId();
		String sourceModuleId = dragDataObject.getSourceId();

		sourceListManagerAdapter.dragEnd(itemID, sourceModuleId);
	}
}
