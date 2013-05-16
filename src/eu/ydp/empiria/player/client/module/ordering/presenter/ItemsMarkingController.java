package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public class ItemsMarkingController {

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
