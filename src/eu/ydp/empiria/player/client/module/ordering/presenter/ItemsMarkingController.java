package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.common.collect.Lists;


import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public class ItemsMarkingController {

	private OrderingItemsDao orderingItemsDao;
	
	public void initialize(OrderingItemsDao orderingItemsDao){
		this.orderingItemsDao = orderingItemsDao;
	}
	
	public List<OrderingItem> findItemsFittingType(MarkAnswersType type, List<Boolean> answerEvaluations, List<String> currentItemsOrder) {
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
	
	public void marOrUnmarkItemsByType(List<OrderingItem> orderingItems, MarkAnswersType type, MarkAnswersMode mode) {
		if(mode == MarkAnswersMode.MARK){
			markItems(orderingItems, type);
		}else if(mode == MarkAnswersMode.UNMARK){
			unmarkItems(orderingItems);
		}
	}

	private void markItems(List<OrderingItem> orderingItems, MarkAnswersType type) {
		if (type == MarkAnswersType.CORRECT) {
			markItemsAsCorrect(orderingItems);
		} else if (type == MarkAnswersType.WRONG) {
			markItemsAsWrong(orderingItems);
		}
	}

	private void markItemsAsWrong(List<OrderingItem> orderingItems) {
		for (OrderingItem orderingItem : orderingItems) {
			orderingItem.setAnswerType(UserAnswerType.WRONG);
		}
	}

	private void markItemsAsCorrect(List<OrderingItem> orderingItems) {
		for (OrderingItem orderingItem : orderingItems) {
			orderingItem.setAnswerType(UserAnswerType.CORRECT);
		}
	}

	private void unmarkItems(List<OrderingItem> orderingItems) {
		for (OrderingItem orderingItem : orderingItems) {
			orderingItem.setAnswerType(UserAnswerType.NONE);
		}
	}

}
