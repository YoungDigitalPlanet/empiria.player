package eu.ydp.empiria.player.client.module.draggap.dragging;

import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.gwtutil.client.Wrapper;

public class SourceListConnectedDragHandler implements DragStartHandler, DragEndHandler{

	private final SourcelistManager sourcelistManager;
	private final String moduleIdentifier;
	private final OverlayTypesParser overlayTypesParser;
	private final Wrapper<String> itemIdWrapper;
	
	@Inject
	public SourceListConnectedDragHandler(
			@Assisted String moduleIdentifier, 
			@Assisted Wrapper<String> itemIdWrapper,
			@PageScoped SourcelistManager sourcelistManager, 
			OverlayTypesParser overlayTypesParser) {
		this.sourcelistManager = sourcelistManager;
		this.moduleIdentifier = moduleIdentifier;
		this.overlayTypesParser = overlayTypesParser;
		this.itemIdWrapper = itemIdWrapper;
	}

	@Override
	public void onDragStart(DragStartEvent event) {
		sourcelistManager.dragStart(moduleIdentifier);

		DragDataObject dataObject = overlayTypesParser.<NativeDragDataObject> get();
		dataObject.setItemId(itemIdWrapper.getInstance());
		dataObject.setSourceId(moduleIdentifier);
		event.setData("json", dataObject.toJSON());
	}

	@Override
	public void onDragEnd(DragEndEvent event) {
		sourcelistManager.dragFinished();
	}
}
