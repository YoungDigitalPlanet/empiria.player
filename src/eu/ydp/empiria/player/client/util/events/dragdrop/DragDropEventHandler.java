package eu.ydp.empiria.player.client.util.events.dragdrop;

import eu.ydp.empiria.player.client.util.events.EventHandler;

public interface DragDropEventHandler extends EventHandler {
	public void onDragEvent(DragDropEvent event);
}
