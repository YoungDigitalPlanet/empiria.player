package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

public interface OrderInteractionPresenter extends ActivityPresenter<OrderInteractionModuleModel, OrderInteractionBean> {

	void updateItemsOrder(List<String> itemsOrder);

	OrderInteractionOrientation getOrientation();
}
