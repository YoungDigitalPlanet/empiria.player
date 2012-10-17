package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleListener;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public interface ConnectionModuleFactory {
	public ConnectionModuleListener getConnectionModuleListener(ConnectionModule module);
	public ConnectionModuleStructure getConnectionModuleStructure();
}
