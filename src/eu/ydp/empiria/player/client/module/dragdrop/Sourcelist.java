package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.List;

public interface Sourcelist {

	String getItemValue(String itemId);

	void useItem(String itemId);

	void restockItem(String itemId);

	void useAndRestockItems(List<String> itemsIds);
}
