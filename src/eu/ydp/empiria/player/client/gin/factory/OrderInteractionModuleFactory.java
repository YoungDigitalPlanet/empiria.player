package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;


public interface OrderInteractionModuleFactory {
	OrderInteractionViewItem getOrderInteractionViewItem(IsWidget widget,String itemId);
}
