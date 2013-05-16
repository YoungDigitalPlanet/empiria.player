package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.SimpleOrderChoiceBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

public class OrderInteractionPresenterImpl implements OrderInteractionPresenter {

	private OrderInteractionView interactionView;
	private ItemsOrderByAnswersFinder itemsOrderByAnswersFinder;
	private ItemsMarkingController itemsMarkingController;
	private OrderingItemsDao orderingItemsDao;
	
	@Inject
	public OrderInteractionPresenterImpl(OrderInteractionView interactionView, ItemsOrderByAnswersFinder itemsOrderByAnswersFinder,
			ItemsMarkingController itemsMarkingController, OrderingItemsDao orderingItemsDao) {
		this.interactionView = interactionView;
		this.itemsOrderByAnswersFinder = itemsOrderByAnswersFinder;
		this.itemsMarkingController = itemsMarkingController;
		this.orderingItemsDao = orderingItemsDao;
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
		List<SimpleOrderChoiceBean> itemBeans = bean.getChoiceBeans();
		InlineBodyGeneratorSocket bodyGeneratorSocket = socket.getInlineBodyGeneratorSocket();
		
		for (int i=0; i<itemBeans.size(); i++) {
			SimpleOrderChoiceBean simpleOrderChoiceBean = itemBeans.get(i);
			XMLContent content = simpleOrderChoiceBean.getContent();
			String answerValue = simpleOrderChoiceBean.getIdentifier();
			String itemId = String.valueOf(i);
			OrderingItem orderingItem = new OrderingItem(itemId, answerValue);
			
			interactionView.createItem(orderingItem, content, bodyGeneratorSocket);
			
			orderingItemsDao.addItem(orderingItem);
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		//sprawdzic aktualn¹ kolejnoœæ
		//mieszac poki jest taka sama
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
		List<OrderingItem> fittingTypeItems = findItemsFittingType(type, answerEvaluations, currentItemsOrder);
		
		return fittingTypeItems;
	}

	private List<OrderingItem> findItemsFittingType(MarkAnswersType type, List<Boolean> answerEvaluations, List<String> currentItemsOrder) {
		List<OrderingItem> fittingTypeItems = Lists.newArrayList();
		
		for(int i=0; i<answerEvaluations.size(); i++){
			String itemId = currentItemsOrder.get(i);
			boolean evaluationResult = answerEvaluations.get(i);
			
			if(isItemFittingEvaluationType(evaluationResult, type)){
				OrderingItem orderingItem = orderingItemsDao.getItem(itemId);
				fittingTypeItems.add(orderingItem);
			}
			
		}
		return fittingTypeItems;
	}

	private boolean isItemFittingEvaluationType(boolean evaluationResult, MarkAnswersType type) {
		if(evaluationResult && type == MarkAnswersType.CORRECT){
			return true;
		}else if( !evaluationResult && type == MarkAnswersType.WRONG){
			return true;
		}
		
		return false;
	}

	private List<String> getCurrentAnswersOrder() {
		List<String> currentAnswers = model.getCurrentAnswers();
		Collection<OrderingItem> items = orderingItemsDao.getItems();
		return itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(currentAnswers, items);
	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		List<String> correctAnswers = model.getCorrectAnswers();
		Collection<OrderingItem> items = orderingItemsDao.getItems();
		List<String> correctItemsOrder = itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(correctAnswers, items);
		
		interactionView.setChildrenOrder(correctItemsOrder);
	}

	@Override
	public void itemClicked(String itemId) {
		// TODO Auto-generated method stub
		//case 1. pierwy element clickniety
		//case 2. drugi element clickniety
		//case 3. element odclikniety
	}
}
