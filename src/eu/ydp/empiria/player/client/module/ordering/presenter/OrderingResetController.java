package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class OrderingResetController {

	private final OrderingAnswersShuffler orderingAnswersShuffler;
	private final OrderingItemsDao orderingItemsDao;
	private final ItemsResponseOrderController itemsResponseOrderController;
	private final OrderInteractionModuleModel model;

	@Inject
	public OrderingResetController(OrderingAnswersShuffler orderingAnswersShuffler, @ModuleScoped ItemsResponseOrderController itemsResponseOrderController,
			@ModuleScoped OrderingItemsDao orderingItemsDao, @ModuleScoped OrderInteractionModuleModel model) {
		this.orderingAnswersShuffler = orderingAnswersShuffler;
		this.orderingItemsDao = orderingItemsDao;
		this.itemsResponseOrderController = itemsResponseOrderController;
		this.model = model;
	}

	public void reset() {
		List<String> newAnswersOrder = orderingAnswersShuffler.shuffleAnswers(model.getCurrentAnswers(), model.getCorrectAnswers());
		List<String> newItemsOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(newAnswersOrder);
		orderingItemsDao.setItemsOrder(newItemsOrder);
		itemsResponseOrderController.updateResponseWithNewOrder(newItemsOrder);
	}

}
