package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.List;

import eu.ydp.empiria.player.client.module.IUniqueModule;

public interface Sourcelist extends IUniqueModule {

	String getItemValue(String itemId);

	void useItem(String itemId);

	void restockItem(String itemId);

	void useAndRestockItems(List<String> itemsIds);
}
