package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;

import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;

public class SourceListViewDropHandler implements DropHandler {

	private final DragDataObjectFromEventExtractor objectFromEventExtractor;
	private final SourceListPresenter sourceListPresenter;

	public SourceListViewDropHandler(DragDataObjectFromEventExtractor objectFromEventExtractor, SourceListPresenter sourceListPresenter) {
		this.objectFromEventExtractor = objectFromEventExtractor;
		this.sourceListPresenter = sourceListPresenter;
	}

	@Override
	public void onDrop(DropEvent event) {
		Optional<DragDataObject> objectFromEvent = objectFromEventExtractor.extractDroppedObjectFromEvent(event);
		if (objectFromEvent.isPresent()) {
			DragDataObject dataObject = objectFromEvent.get();
			sourceListPresenter.onDropEvent(dataObject.getItemId(), dataObject.getSourceId());
		}
	}

}
