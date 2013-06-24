package eu.ydp.empiria.player.client.module.dragdrop;

import eu.ydp.empiria.player.client.module.IModule;

public interface SourcelistClient extends IModule {

	String getDragItemId();

	void setDragItem(String itemId);

	void removeDragItem();
	
	void lockDragZone();
	
	void unlockDragZone();
}
