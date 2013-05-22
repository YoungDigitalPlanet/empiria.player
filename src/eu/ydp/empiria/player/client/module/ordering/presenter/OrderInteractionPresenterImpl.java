package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.Collection;
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
	private final ItemsOrderByAnswersFinder itemsOrderByAnswersFinder;
	private final ItemsMarkingController itemsMarkingController;
	private final OrderingItemsDao orderingItemsDao;
	private final OrderingAnswersShuffler orderingAnswersShuffler;
	private final ItemClickController itemClickController;
	private final OrderInteractionModuleFactory orderInteractionModuleFactory;
	
	@Inject
	public OrderInteractionPresenterImpl(
			OrderInteractionView interactionView, 
			ItemsOrderByAnswersFinder itemsOrderByAnswersFinder,
			ItemsMarkingController itemsMarkingController, 
			OrderingItemsDao orderingItemsDao, 
			OrderingAnswersShuffler orderingAnswersShuffler,
			ItemClickController itemClickController, 
			OrderInteractionModuleFactory orderInteractionModuleFactory) {
		this.interactionView = interactionView;
		this.itemsOrderByAnswersFinder = itemsOrderByAnswersFinder;
		this.itemsMarkingController = itemsMarkingController;
		this.orderingItemsDao = orderingItemsDao;
		this.orderingAnswersShuffler = orderingAnswersShuffler;
		this.itemClickController = itemClickController;
		this.orderInteractionModuleFactory = orderInteractionModuleFactory;
	}

	private ModuleSocket socket;
	private OrderInteractionModuleModel model;
	private OrderInteractionBean bean;

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}

	@Override
	public void bindView() {
		OrderItemClickListener orderItemClickListener = new OrderItemClickListenerImpl(this);
		interactionView.setClickListener(orderItemClickListener);

		InlineBodyGeneratorSocket bodyGeneratorSocket = socket.getInlineBodyGeneratorSocket();
		OrderingViewBuilder viewBuilder = orderInteractionModuleFactory.getViewBuilder(bodyGeneratorSocket, bean, interactionView, orderingItemsDao);
		viewBuilder.buildView();
		
		
		initializeOtherModules();
	}

	private void initializeOtherModules() {
		itemClickController.initialize(orderingItemsDao, model);
		itemsMarkingController.initialize(orderingItemsDao);
	}

	@Override
	public void reset() {
		List<String> newAnswersOrder = orderingAnswersShuffler.shuffleAnswers(model.getCurrentAnswers(), model.getCorrectAnswers());
		List<String> newItemsOrder = getCorrectItemsOrderByAnswers(newAnswersOrder);
		interactionView.setChildrenOrder(newItemsOrder);
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
		List<OrderingItem> orderingItems = getItemsByEvaluationType(type);
		itemsMarkingController.marOrUnmarkItemsByType(orderingItems, type, mode);
		updateAllItemsStyles();
	}
	
	public List<OrderingItem> getItemsByEvaluationType(MarkAnswersType type){
		List<Boolean> answerEvaluations = socket.evaluateResponse(model.getResponse());
		List<String> currentItemsOrder = getCurrentAnswersOrder();
		List<OrderingItem> fittingTypeItems = itemsMarkingController.findItemsFittingType(type, answerEvaluations, currentItemsOrder);
		
		return fittingTypeItems;
	}

	private List<String> getCurrentAnswersOrder() {
		List<String> currentAnswers = model.getCurrentAnswers();
		return getCorrectItemsOrderByAnswers(currentAnswers);
	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		List<String> correctAnswers = model.getCorrectAnswers();
		List<String> correctItemsOrder = getCorrectItemsOrderByAnswers(correctAnswers);
		interactionView.setChildrenOrder(correctItemsOrder);
	}

	private List<String> getCorrectItemsOrderByAnswers(List<String> answers) {
		Collection<OrderingItem> items = orderingItemsDao.getItems();
		List<String> correctItemsOrder = itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(answers, items);
		return correctItemsOrder;
	}

	@Override
	public void itemClicked(String itemId) {
		ItemClickAction itemClickAction = itemClickController.itemClicked(itemId);
		
		if(itemClickAction == ItemClickAction.SELECT && itemClickAction == ItemClickAction.UNSELECT){
			OrderingItem orderingItem = orderingItemsDao.getItem(itemId);
			interactionView.setChildStyles(orderingItem);
		}
		
		updateAllItemsStyles();
		updateCurrentAnswersOrder();
	}

	private void updateCurrentAnswersOrder() {
		List<String> currentAnswersOrder = getCurrentAnswersOrder();
		interactionView.setChildrenOrder(currentAnswersOrder);
	}
}
