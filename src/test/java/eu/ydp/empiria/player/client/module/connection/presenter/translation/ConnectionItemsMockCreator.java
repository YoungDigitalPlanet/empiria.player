package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class ConnectionItemsMockCreator {

	public ConnectionItems createConnectionItems(int countLeft, int itemOffsetLeft, int countRight, int itemOffsetRight, int itemWidth) {
		ConnectionItems items = mock(ConnectionItems.class);

		ArrayList<ConnectionItem> lefts = createItemsCollection(countLeft, itemOffsetLeft, itemWidth);
		stub(items.getLeftItems()).toReturn(lefts);

		ArrayList<ConnectionItem> rights = createItemsCollection(countRight, itemOffsetRight, itemWidth);
		stub(items.getRightItems()).toReturn(rights);

		List<ConnectionItem> allItems = Lists.newArrayList();
		allItems.addAll(lefts);
		allItems.addAll(rights);
		stub(items.getAllItems()).toReturn(allItems);

		return items;
	}

	private ArrayList<ConnectionItem> createItemsCollection(int count, int offsetLeft, int width) {
		ArrayList<ConnectionItem> lefts = new ArrayList<ConnectionItem>();
		for (int i = 0; i < count; i++) {
			ConnectionItem item = createItem(offsetLeft, width);
			lefts.add(item);
		}
		return lefts;
	}

	private ConnectionItem createItem(int left, int width) {
		ConnectionItem item = mock(ConnectionItem.class);
		stub(item.getOffsetLeft()).toReturn(left);
		stub(item.getWidth()).toReturn(width);
		return item;
	}
}
