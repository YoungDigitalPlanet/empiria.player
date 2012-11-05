package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.choice.ChoiceModule;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;

public interface ModuleFactory {
	public ConnectionModule getConnectionModule();
	public ChoiceModule getChoiceModule();
	public ObjectModule getObjectModule();
}
