package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;

public class ItemsResponseOrderController {

	private final ItemsOrderByAnswersFinder itemsOrderByAnswersFinder;
	private OrderingItemsDao orderingItemsDao;
	private OrderInteractionModuleModel model;
	
	@Inject
	public ItemsResponseOrderController(ItemsOrderByAnswersFinder itemsOrderByAnswersFinder) {
		this.itemsOrderByAnswersFinder = itemsOrderByAnswersFinder;
	}

	public void initialize(OrderingItemsDao orderingItemsDao, OrderInteractionModuleModel model){
		this.orderingItemsDao = orderingItemsDao;
		this.model = model;
	}
	
	public List<String> getResponseAnswersByItemsOrder(List<String> itemsOrder){
		List<String> responseAnswers = Lists.newArrayList();
		for (String itemId : itemsOrder) {
			OrderingItem item = orderingItemsDao.getItem(itemId);
			String answerValue = item.getAnswerValue();
			responseAnswers.add(answerValue);
		}
		
		return responseAnswers;
	}
	
	public List<String> getCorrectItemsOrderByAnswers(List<String> answers) {
		Collection<OrderingItem> items = orderingItemsDao.getItems();
		List<String> correctItemsOrder = itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(answers, items);
		return correctItemsOrder;
	}
	

	public void updateResponseWithNewOrder(List<String> newItemsOrder) {
		List<String> responseAnswers = getResponseAnswersByItemsOrder(newItemsOrder);
		model.getResponse().values = new ArrayList<String>(responseAnswers);
	}
	
	public List<String> getCurrentItemsOrderByAnswers() {
		List<String> currentAnswers = model.getCurrentAnswers();
		return getCorrectItemsOrderByAnswers(currentAnswers);
	}
	
}
