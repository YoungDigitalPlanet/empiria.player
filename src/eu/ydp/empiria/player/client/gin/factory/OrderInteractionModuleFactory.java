package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;

public interface OrderInteractionModuleFactory {
	OrderInteractionModuleModel getOrderInteractionModuleModel(Response response, ResponseModelChangeListener responseModelChange);
	OrderInteractionViewItem getOrderInteractionViewItem(IsWidget widget,String itemId);
}
