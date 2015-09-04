package eu.ydp.empiria.player.client.module.ordering.view.items;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ordering.OrderingStyleNameConstants;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

import java.util.Map;
import java.util.Set;

public class OrderInteractionViewItemStylesImpl implements OrderInteractionViewItemStyles {

    @Inject
    private OrderingStyleNameConstants styleNames;
    private Map<UserAnswerType, String> stylesMap = Maps.newHashMap();
    private static final String SEPARATOR = " ";

    @Inject
    public OrderInteractionViewItemStylesImpl(OrderingStyleNameConstants styleNames) {
        this.styleNames = styleNames;

        stylesMap.put(UserAnswerType.CORRECT, styleNames.QP_ORDERED_ITEM_CORRECT());
        stylesMap.put(UserAnswerType.WRONG, styleNames.QP_ORDERED_ITEM_WRONG());
        stylesMap.put(UserAnswerType.NONE, styleNames.QP_ORDERED_ITEM_NONE());
        stylesMap.put(UserAnswerType.DEFAULT, styleNames.QP_ORDERED_ITEM_DEFAULT());
    }

    @Override
    public void applyStylesOnWidget(OrderingItem orderingItem, OrderInteractionViewItem viewItem) {
        String styleToApply = buildStyleName(orderingItem);
        viewItem.asWidget()
                .setStyleName(styleToApply);
    }

    private String buildStyleName(OrderingItem orderingItem) {
        String itemStyle = styleNames.QP_ORDERED_ITEM();
        String answerTypeStyle = stylesMap.get(orderingItem.getAnswerType());

        Set<String> styles = Sets.newLinkedHashSet();
        styles.add(itemStyle);
        styles.add(answerTypeStyle);

        if (orderingItem.isLocked()) {
            styles.add(styleNames.QP_ORDERED_ITEM_LOCKED());
        }

        if (orderingItem.isSelected()) {
            styles.add(styleNames.QP_ORDERED_ITEM_SELECTED());
        }

        return Joiner.on(SEPARATOR)
                .join(styles);
    }
}
