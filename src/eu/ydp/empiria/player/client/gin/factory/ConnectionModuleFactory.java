package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;


public interface ConnectionModuleFactory {
	//public ConnectionModuleListener getConnectionModuleListener(ConnectionModule module);
	public ConnectionModuleModel getConnectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener);
}
