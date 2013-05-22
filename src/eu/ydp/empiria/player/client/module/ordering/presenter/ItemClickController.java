package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.ItemClickAction;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;

public class ItemClickController {

	private OrderingItemsDao orderingItemsDao;
	private OrderInteractionModuleModel interactionModuleModel;
	
	public void initialize(OrderingItemsDao orderingItemsDao, OrderInteractionModuleModel interactionModuleModel){
		this.orderingItemsDao = orderingItemsDao;
		this.interactionModuleModel = interactionModuleModel;
	}
	
	public ItemClickAction itemClicked(String itemId) {
		Optional<OrderingItem> lastClickedItem = orderingItemsDao.getLastClickedItem();
		OrderingItem clickedItem = orderingItemsDao.getItem(itemId);
		
		ItemClickAction itemClickAction;
		if(isFirstItemClicked(lastClickedItem)){
			selectItem(clickedItem);
			itemClickAction = ItemClickAction.SELECT;
		} else if(isSameItemClickedAgain(lastClickedItem, clickedItem)){
			itemClickAction = ItemClickAction.UNSELECT;
			unselectItem(clickedItem);
		} else {
			itemClickAction = ItemClickAction.SWICTH;
			switchItems(lastClickedItem, clickedItem);
		}
		return itemClickAction;
	}
	
	private boolean isFirstItemClicked(Optional<OrderingItem> lastClickedItem) {
		return lastClickedItem.isPresent() == false;
	}
	
	private void selectItem(OrderingItem clickedItem) {
		clickedItem.setSelected(true);
	}
	
	private boolean isSameItemClickedAgain(Optional<OrderingItem> lastClickedItem, OrderingItem clickedItem) {
		OrderingItem orderingItem = lastClickedItem.get();
		boolean isSameItem = clickedItem.equals(orderingItem);
		return isSameItem;
	}
	
	private void unselectItem(OrderingItem clickedItem) {
		clickedItem.setSelected(false);
	}

	private void switchItems(Optional<OrderingItem> lastClickedItem, OrderingItem clickedItem) {
		OrderingItem previousItem = lastClickedItem.get();
		unselectItem(previousItem);
		
		String clickedItemAnswer = clickedItem.getAnswerValue();
		String previousClickedItemAnswer = previousItem.getAnswerValue();
		
		interactionModuleModel.swicthAnswers(clickedItemAnswer, previousClickedItemAnswer);
	}
}
