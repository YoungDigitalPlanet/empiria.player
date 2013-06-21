package eu.ydp.empiria.player.client.module.dragdrop;

public interface SourcelistClient {

	void setDragItem(String itemId);

	void removeDragItem();

	String getDragItemId();
}
