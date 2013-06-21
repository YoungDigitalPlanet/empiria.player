package eu.ydp.empiria.player.client.module.dragdrop;

public interface SourcelistClient {

	String getDragItemId();

	void setDragItem(String itemId);

	void removeDragItem();
}
