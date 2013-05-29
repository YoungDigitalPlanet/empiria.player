package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
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
	private final OrderInteractionModuleFactory orderInteractionModuleFactory;
	private final ItemsResponseOrderController itemsResponseOrderController;
	private final OrderingResetController orderingResetController;
	private final OrderingShowingAnswersController showingAnswersController;

	private ModuleSocket socket;
	private OrderInteractionModuleModel model;
	private OrderInteractionBean bean;

	@Inject
	public OrderInteractionPresenterImpl(
			OrderInteractionView interactionView,
			ItemsMarkingController itemsMarkingController,
			OrderingItemsDao orderingItemsDao,
			ItemClickController itemClickController,
			OrderInteractionModuleFactory orderInteractionModuleFactory,
			ItemsResponseOrderController itemsResponseOrderController,
			OrderingResetController orderingResetController,
			OrderingShowingAnswersController showingAnswersController) {
		this.interactionView = interactionView;
		this.itemsMarkingController = itemsMarkingController;
		this.orderingItemsDao = orderingItemsDao;
		this.itemClickController = itemClickController;
		this.orderInteractionModuleFactory = orderInteractionModuleFactory;
		this.itemsResponseOrderController = itemsResponseOrderController;
		this.orderingResetController = orderingResetController;
		this.showingAnswersController = showingAnswersController;
	}

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}

	@Override
	public void bindView() {
		OrderItemClickListener orderItemClickListener = new OrderItemClickListenerImpl(this);
		interactionView.setClickListener(orderItemClickListener);
		initializeSubModules();

		InlineBodyGeneratorSocket bodyGeneratorSocket = socket.getInlineBodyGeneratorSocket();
		OrderingViewBuilder viewBuilder = orderInteractionModuleFactory.getViewBuilder(bodyGeneratorSocket, bean, interactionView, orderingItemsDao);
		viewBuilder.buildView();

		itemsResponseOrderController.updateResponseWithNewOrder(orderingItemsDao.getItemsOrder());
		reset();
	}

	private void initializeSubModules() {
		itemClickController.initialize(orderingItemsDao);
		itemsMarkingController.initialize(orderingItemsDao, itemsResponseOrderController, model);
		itemsResponseOrderController.initialize(orderingItemsDao, model);
		orderingResetController.initialize(orderingItemsDao, itemsResponseOrderController, model);
		showingAnswersController.initialize(orderingItemsDao, itemsResponseOrderController, model);
	}

	@Override
	public void reset() {
		orderingResetController.reset();
		interactionView.setChildrenOrder(orderingItemsDao.getItemsOrder());
	}

	@Override
	public void setModel(OrderInteractionModuleModel model) {
		this.model = model;
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
