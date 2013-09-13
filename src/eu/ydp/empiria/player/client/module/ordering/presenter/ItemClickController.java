package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.model.ItemClickAction;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ItemClickController {

	private final OrderingItemsDao orderingItemsDao;
	
	@Inject
	public ItemClickController(@ModuleScoped OrderingItemsDao orderingItemsDao) {
		this.orderingItemsDao = orderingItemsDao;
	}

	public ItemClickAction itemClicked(String itemId) {
		Optional<OrderingItem> lastClickedItem = orderingItemsDao.getLastClickedItem();
		OrderingItem clickedItem = orderingItemsDao.getItem(itemId);

		ItemClickAction itemClickAction;
		if (clickedItem.isLocked()) {
			itemClickAction = ItemClickAction.LOCK;
		} else if (isFirstItemClicked(lastClickedItem)) {
			selectItem(clickedItem);
			itemClickAction = ItemClickAction.SELECT;
		} else if (isSameItemClickedAgain(lastClickedItem, clickedItem)) {
			itemClickAction = ItemClickAction.UNSELECT;
			unselectItem(clickedItem);
		} else {
			itemClickAction = ItemClickAction.SWITCH;
			switchItems(lastClickedItem, clickedItem);
		}
		return itemClickAction;
	}

	private boolean isFirstItemClicked(Optional<OrderingItem> lastClickedItem) {
		return lastClickedItem.isPresent() == false;
	}

	private void selectItem(OrderingItem clickedItem) {
		clickedItem.setSelected(true);
		orderingItemsDao.setLastClickedItem(clickedItem);
	}

	private boolean isSameItemClickedAgain(Optional<OrderingItem> lastClickedItem, OrderingItem clickedItem) {
		OrderingItem orderingItem = lastClickedItem.get();
		boolean isSameItem = clickedItem.equals(orderingItem);
		return isSameItem;
	}

	private void unselectItem(OrderingItem clickedItem) {
		clickedItem.setSelected(false);
		orderingItemsDao.setLastClickedItem(null);
	}

	private void switchItems(Optional<OrderingItem> lastClickedItem, OrderingItem clickedItem) {
		OrderingItem previousItem = lastClickedItem.get();
		unselectItem(previousItem);

		String previousItemId = previousItem.getId();
		String clickedItemId = clickedItem.getId();

		switchItemsInCurrentOrder(previousItemId, clickedItemId);
	}

	private void switchItemsInCurrentOrder(String previousItemId, String clickedItemId) {
		List<String> itemsOrder = orderingItemsDao.getItemsOrder();

		int previousItemIndex = itemsOrder.indexOf(previousItemId);
		int clickedItemIndex = itemsOrder.indexOf(clickedItemId);

		itemsOrder.set(clickedItemIndex, previousItemId);
		itemsOrder.set(previousItemIndex, clickedItemId);
	}
}
