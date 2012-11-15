package eu.ydp.empiria.player.client.module.dragdrop;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventHandler;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

public class DragDropManager implements Extension, DragDropEventHandler {

	@Inject
	EventsBus eventsBus;

	@Override
	public ExtensionType getType() {
		return ExtensionType.MULTITYPE;
	}

	@Override
	public void init() {
		eventsBus.addHandler(DragDropEvent.getTypes(DragDropEventTypes.values()), this);
	}

	private void handleDragStart(DragDropEvent event){
		IModule sourceModule = event.getModule();

	}

	@Override
	public void onDragEvent(DragDropEvent event) {
		switch (event.getType()) {
		case DRAG_START:
			break;
		case DRAG_CANCELL:
			break;
		case DRAG_END:
			break;
		case REGISTER_DROP_ZONE:
			break;
		default:
			break;
		}
	}


}