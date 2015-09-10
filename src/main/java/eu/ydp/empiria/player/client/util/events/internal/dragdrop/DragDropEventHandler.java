package eu.ydp.empiria.player.client.util.events.internal.dragdrop;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface DragDropEventHandler extends EventHandler {
    public void onDragEvent(DragDropEvent event);
}
