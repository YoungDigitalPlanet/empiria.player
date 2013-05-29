package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.ItemClickAction;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;

public class OrderInteractionPresenterImpl implements OrderInteractionPresenter {

	private final OrderInteractionView interactionView;
	private final ItemsMarkingController itemsMarkingController;
	private final OrderingItemsDao orderingItemsDao;
	private final ItemClickController itemClickController;
	private final ItemsResponseOrderController itemsResponseOrderController;
	private final OrderingResetController orderingResetController;
	private final OrderingShowingAnswersController showingAnswersController;
	private final OrderInteractionModuleModel model;
	private final OrderingViewBuilder viewBuilder;
	
	private ModuleSocket socket;
	private OrderInteractionBean bean;

	@Inject
	public OrderInteractionPresenterImpl(
			ItemsMarkingController itemsMarkingController, 
			ItemClickController itemClickController, 
			ItemsResponseOrderController itemsResponseOrderController,
			OrderingResetController orderingResetController,
			OrderingShowingAnswersController showingAnswersController,
			OrderingViewBuilder viewBuilder,
			@ModuleScoped OrderInteractionView interactionView, 
			@ModuleScoped OrderingItemsDao orderingItemsDao,
			@ModuleScoped OrderInteractionModuleModel model) {
		this.viewBuilder = viewBuilder;
		this.interactionView = interactionView;
		this.itemsMarkingController = itemsMarkingController;
		this.orderingItemsDao = orderingItemsDao;
		this.itemClickController = itemClickController;
		this.itemsResponseOrderController = itemsResponseOrderController;
		this.orderingResetController = orderingResetController;
		this.showingAnswersController = showingAnswersController;
		this.model = model;
	}

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}

	@Override
	public void bindView() {
		OrderItemClickListener orderItemClickListener = new OrderItemClickListenerImpl(this);
		interactionView.setClickListener(orderItemClickListener);

		InlineBodyGeneratorSocket bodyGeneratorSocket = socket.getInlineBodyGeneratorSocket();
		viewBuilder.buildView(bean, bodyGeneratorSocket);

		itemsResponseOrderController.updateResponseWithNewOrder(orderingItemsDao.getItemsOrder());
		reset();
	}

	@Override
	public void reset() {
		orderingResetController.reset();
		interactionView.setChildrenOrder(orderingItemsDao.getItemsOrder());
	}

	@Override
	public void setModel(OrderInteractionModuleModel model) {
		//unused method - will be removed in the future
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		this.socket = socket;
	}

	@Override
	public void setBean(OrderInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setLocked(boolean locked) {
		for(OrderingItem orderingItem : orderingItemsDao.getItems()){
			orderingItem.setLocked(locked);
		}
		updateAllItemsStyles();
	}

	private void updateAllItemsStyles() {
		for(OrderingItem orderingItem : orderingItemsDao.getItems()){
			interactionView.setChildStyles(orderingItem);
		}
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		itemsMarkingController.markOrUnmarkItemsByType(type, mode);
		updateAllItemsStyles();
	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		List<String> answerOrder = showingAnswersController.findNewAnswersOrderToShow(mode);
		interactionView.setChildrenOrder(answerOrder);
	}

	@Override
	public void itemClicked(String itemId) {
		ItemClickAction itemClickAction = itemClickController.itemClicked(itemId);
		if(itemClickAction != ItemClickAction.LOCK) {
			if(itemClickAction == ItemClickAction.SELECT || itemClickAction == ItemClickAction.UNSELECT){
				OrderingItem orderingItem = orderingItemsDao.getItem(itemId);
				interactionView.setChildStyles(orderingItem);
			}else{
				updateAllItemsStyles();
				updateItemsOrderInView();
				itemsResponseOrderController.updateResponseWithNewOrder(orderingItemsDao.getItemsOrder());
				model.onModelChange();
			}
		}
	}

	private void updateItemsOrderInView() {
		List<String> currentAnswersOrder = orderingItemsDao.getItemsOrder();
		interactionView.setChildrenOrder(currentAnswersOrder);
	}
}
