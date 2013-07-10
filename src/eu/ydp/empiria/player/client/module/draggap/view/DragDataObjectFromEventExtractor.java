package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;

public class DragDataObjectFromEventExtractor {

	private final OverlayTypesParser overlayTypesParser;

	@Inject
	public DragDataObjectFromEventExtractor(OverlayTypesParser overlayTypesParser) {
		this.overlayTypesParser = overlayTypesParser;
	}
	
	public Optional<DragDataObject> extractDroppedObjectFromEvent(DropEvent dropEvent) {
		String jsonObject = dropEvent.getData("json");
		Optional<DragDataObject> dragData;
		if (overlayTypesParser.isValidJSON(jsonObject)) {
			NativeDragDataObject nativeDragData = overlayTypesParser.get(jsonObject);
			DragDataObject dragDataObject = nativeDragData;
			dragData = Optional.fromNullable(dragDataObject);
		} else {
			dragData = Optional.absent();
		}
		dropEvent.stopPropagation();
		dropEvent.preventDefault();

		return dragData;
	}
}
