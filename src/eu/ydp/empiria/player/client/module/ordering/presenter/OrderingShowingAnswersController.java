package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;

public class OrderingShowingAnswersController {

	private OrderingItemsDao orderingItemsDao;
	private ItemsResponseOrderController itemsResponseOrderController;
	private OrderInteractionModuleModel model;
	
	public void intialize(OrderingItemsDao orderingItemsDao, ItemsResponseOrderController itemsResponseOrderController, OrderInteractionModuleModel model){
		this.orderingItemsDao = orderingItemsDao;
		this.itemsResponseOrderController = itemsResponseOrderController;
		this.model = model;
	}
	
	public List<String> findNewAnswersOrderToShow(ShowAnswersType mode) {
		List<String> answersOrder;
		
		if(mode == ShowAnswersType.CORRECT){
			List<String> correctAnswers = model.getCorrectAnswers();
			answersOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(correctAnswers);
		} else{
			List<String> currentAnswers = model.getCurrentAnswers();
			answersOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(currentAnswers);
			orderingItemsDao.setItemsOrder(answersOrder);
		}
		return answersOrder;
	}
}
