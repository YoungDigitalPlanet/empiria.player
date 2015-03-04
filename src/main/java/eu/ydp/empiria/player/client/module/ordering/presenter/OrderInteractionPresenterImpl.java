package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class OrderInteractionPresenterImpl implements OrderInteractionPresenter {

	private final OrderInteractionView interactionView;
	private final ItemsMarkingController itemsMarkingController;
	private final OrderingItemsDao orderingItemsDao;
	private final ItemsResponseOrderController itemsResponseOrderController;
	private final OrderingResetController orderingResetController;
	private final OrderingShowingAnswersController showingAnswersController;
	private final OrderingViewBuilder viewBuilder;
	private final OrderInteractionModuleModel model;
	private final DragController dragController;

	private ModuleSocket socket;
	private OrderInteractionBean bean;

	@Inject
	public OrderInteractionPresenterImpl(ItemsMarkingController itemsMarkingController,
			@ModuleScoped ItemsResponseOrderController itemsResponseOrderController, OrderingResetController orderingResetController,
			OrderingShowingAnswersController showingAnswersController, OrderingViewBuilder viewBuilder, @ModuleScoped OrderInteractionView interactionView,
			@ModuleScoped OrderingItemsDao orderingItemsDao, @ModuleScoped OrderInteractionModuleModel model, @ModuleScoped DragController dragController) {
		this.viewBuilder = viewBuilder;
		this.interactionView = interactionView;
		this.itemsMarkingController = itemsMarkingController;
		this.orderingItemsDao = orderingItemsDao;
		this.itemsResponseOrderController = itemsResponseOrderController;
		this.orderingResetController = orderingResetController;
		this.showingAnswersController = showingAnswersController;
		this.dragController = dragController;
		this.model = model;
	}

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}

	@Override
	public void bindView() {
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
		// unused method - will be removed in the future
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
		disableOrEnableDrag(locked);
		for (OrderingItem orderingItem : orderingItemsDao.getItems()) {
			orderingItem.setLocked(locked);
		}
		updateAllItemsStyles();
	}

	private void disableOrEnableDrag(boolean disable) {
		if (disable) {
			dragController.disableDrag();
		} else {
			dragController.enableDrag();
		}
	}

	private void updateAllItemsStyles() {
		for (OrderingItem orderingItem : orderingItemsDao.getItems()) {
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
	public void updateItemsOrder(List<String> newItemsOrder) {
		orderingItemsDao.setItemsOrder(newItemsOrder);
		updateAllItemsStyles();
		interactionView.setChildrenOrder(newItemsOrder);
		itemsResponseOrderController.updateResponseWithNewOrder(newItemsOrder);
		model.onModelChange();
	}

	@Override
	public OrderInteractionOrientation getOrientation() {
		return bean.getOrientation();
	}
}
