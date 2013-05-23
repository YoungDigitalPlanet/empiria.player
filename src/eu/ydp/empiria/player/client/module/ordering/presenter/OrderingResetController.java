package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;

public class OrderingResetController {

	private final OrderingAnswersShuffler orderingAnswersShuffler;
	private OrderingItemsDao orderingItemsDao;
	private ItemsResponseOrderController itemsResponseOrderController;
	private OrderInteractionModuleModel model;
	
	@Inject
	public OrderingResetController(OrderingAnswersShuffler orderingAnswersShuffler) {
		this.orderingAnswersShuffler = orderingAnswersShuffler;
	}

	public void initialize(OrderingItemsDao orderingItemsDao, ItemsResponseOrderController itemsResponseOrderController, OrderInteractionModuleModel model){
		this.orderingItemsDao = orderingItemsDao;
		this.itemsResponseOrderController = itemsResponseOrderController;
		this.model = model;
	}

	public void reset(){
		List<String> newAnswersOrder = orderingAnswersShuffler.shuffleAnswers(model.getCurrentAnswers(), model.getCorrectAnswers());
		List<String> newItemsOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(newAnswersOrder);
		orderingItemsDao.setItemsOrder(newItemsOrder);
		itemsResponseOrderController.updateResponseWithNewOrder(newItemsOrder);
	}
	
}
