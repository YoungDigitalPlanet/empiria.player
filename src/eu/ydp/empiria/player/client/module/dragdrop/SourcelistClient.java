package eu.ydp.empiria.player.client.module.dragdrop;

import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.view.HasDimensions;

public interface SourcelistClient extends IUniqueModule {

	String getDragItemId();

	void setDragItem(String itemId);

	void removeDragItem();
	
	void lockDropZone();
	
	void unlockDropZone();
	
	void setSize(HasDimensions size);
	
	String getSourcelistId();
}
