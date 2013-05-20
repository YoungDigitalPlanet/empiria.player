package eu.ydp.empiria.player.client.module.ordering.view.items;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class OrderInteractionViewItemStylesImpl implements OrderInteractionViewItemStyles {

	@Inject
	private StyleNameConstants styleNames;
	private Map<UserAnswerType, String> stylesMap;

	@PostConstruct
	public void postConstruct() {
		stylesMap = Maps.newHashMap();

		stylesMap.put(UserAnswerType.CORRECT, styleNames.QP_ORDERED_ITEM_CORRECT());
		stylesMap.put(UserAnswerType.WRONG, styleNames.QP_ORDERED_ITEM_WRONG());
		stylesMap.put(UserAnswerType.NONE, styleNames.QP_ORDERED_ITEM_NONE());
		stylesMap.put(UserAnswerType.DEFAULT, styleNames.QP_ORDERED_ITEM_DEFAULT());

	}

	@Override
	public void applyStylesOnWidget(OrderingItem orderingItem, OrderInteractionViewItem viewItem) {
		String styleToApply = buildStyleName(orderingItem);
		viewItem.asWidget().setStyleName(styleToApply);
	}

	private String buildStyleName(OrderingItem orderingItem) {
		StringBuilder styleToApply = new StringBuilder(stylesMap.get(orderingItem.getAnswerType()));
		if (orderingItem.isLocked()) {
			styleToApply.append(" ").append(styleNames.QP_ORDERED_ITEM_LOCKED());
		}
		if (orderingItem.isSelected()) {
			styleToApply.append(" ").append(styleNames.QP_ORDERED_ITEM_SELECTED());
		}
		return styleToApply.toString();
	}
}
