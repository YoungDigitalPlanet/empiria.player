package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.Collection;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;

public class ItemsOrderByAnswersFinder {

	public List<String> findCorrectItemsOrderByAnswers(List<String> currentAnswers, Collection<OrderingItem> items) {
		List<String> itemsIdOrder = Lists.newArrayList();
		
		for(int i=0; i<currentAnswers.size(); i++){
			String currentAnswer = currentAnswers.get(i);
			OrderingItem item = findItemWithAnswer(currentAnswer, items);
			String itemId = item.getId();
			itemsIdOrder.add(itemId);
			
			items.remove(item);
		}
		
		return itemsIdOrder;
	}

	private OrderingItem findItemWithAnswer(String currentAnswer, Collection<OrderingItem> items) {
		for (OrderingItem orderingItem : items) {
			if(orderingItem.getAnswerValue().equals(currentAnswer)){
				return orderingItem;
			}
		}
		throw new RuntimeException("Cannot match ordering items to user answers!");
	}
	
}
