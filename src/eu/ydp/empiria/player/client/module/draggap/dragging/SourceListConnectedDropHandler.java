package eu.ydp.empiria.player.client.module.draggap.dragging;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;

public class SourceListConnectedDropHandler implements DropHandler{

		private final String moduleIdentifier;
		private final SourcelistManager sourcelistManager;
		private final DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor;
		
		@Inject
		public SourceListConnectedDropHandler(
				@Assisted String moduleIdentifier, 
				@PageScoped SourcelistManager sourcelistManager,
				DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor) {
			this.moduleIdentifier = moduleIdentifier;
			this.sourcelistManager = sourcelistManager;
			this.dragDataObjectFromEventExtractor = dragDataObjectFromEventExtractor;
		}

		@Override
		public void onDrop(DropEvent event) {
			Optional<DragDataObject> objectFromEvent = dragDataObjectFromEventExtractor.extractDroppedObjectFromEvent(event);
			if(objectFromEvent.isPresent()){
				informSourceListManagerAboutDrop(objectFromEvent.get());
			}
		}

		private void informSourceListManagerAboutDrop(DragDataObject dragDataObject) {
			String itemID = dragDataObject.getItemId();
			String sourceModuleId = dragDataObject.getSourceId();
			String targetModuleId = moduleIdentifier;

			sourcelistManager.dragEnd(itemID, sourceModuleId,
					targetModuleId);
		}
}
