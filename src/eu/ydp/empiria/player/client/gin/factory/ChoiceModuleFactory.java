package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;

public interface ChoiceModuleFactory {
	public ChoiceModuleModel getChoiceModuleModel(Response response);
}
