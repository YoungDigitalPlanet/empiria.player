package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderingViewBuilder;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;


public interface OrderInteractionModuleFactory {
	OrderInteractionModuleModel getOrderInteractionModuleModel(Response response, ResponseModelChangeListener responseModelChange);
	
	OrderingViewBuilder getViewBuilder(InlineBodyGeneratorSocket bodyGeneratorSocket, OrderInteractionBean bean, OrderInteractionView interactionView,
			OrderingItemsDao orderingItemsDao);

	OrderInteractionViewItem getOrderInteractionViewItem(IsWidget widget,String itemId);
}
