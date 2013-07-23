package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.List;

import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListLocking;


public interface Sourcelist extends IUniqueModule, SourceListLocking {

	SourcelistItemValue getItemValue(String itemId);

	void useItem(String itemId);

	void restockItem(String itemId);

	void useAndRestockItems(List<String> itemsIds);
	
	HasDimensions getItemSize();
}
