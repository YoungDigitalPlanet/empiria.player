package eu.ydp.empiria.player.client.module.draggap.dragging;

import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.gwtutil.client.Wrapper;

public class SourceListConnectedDragHandler implements DragStartHandler, DragEndHandler{

	private final SourceListManagerAdapter sourceListManagerAdapter;
	private final OverlayTypesParser overlayTypesParser;
	private String moduleIdentifier;
	private Wrapper<String> itemIdWrapper;
	
	@Inject
	public SourceListConnectedDragHandler(
			@ModuleScoped SourceListManagerAdapter sourceListManagerAdapter, 
			OverlayTypesParser overlayTypesParser) {
		this.sourceListManagerAdapter = sourceListManagerAdapter;
		this.overlayTypesParser = overlayTypesParser;
	}
	
	public void initialize(String moduleIdentifier, Wrapper<String> itemIdWrapper) {
		this.moduleIdentifier = moduleIdentifier;
		this.itemIdWrapper = itemIdWrapper;
	}

	@Override
	public void onDragStart(DragStartEvent event) {
		sourceListManagerAdapter.dragStart();

		DragDataObject dataObject = overlayTypesParser.<NativeDragDataObject> get();
		dataObject.setItemId(itemIdWrapper.getInstance());
		dataObject.setSourceId(moduleIdentifier);
		event.setData("json", dataObject.toJSON());
	}

	@Override
	public void onDragEnd(DragEndEvent event) {
		sourceListManagerAdapter.dragFinished();
	}
}
