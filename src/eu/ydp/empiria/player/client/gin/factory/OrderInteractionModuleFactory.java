package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;

public interface OrderInteractionModuleFactory {
	OrderInteractionModuleModel getOrderInteractionModuleModel(Response response, ResponseModelChangeListener responseModelChange);
}
