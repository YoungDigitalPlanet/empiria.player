package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.choice.ChoiceModule;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;

public interface ModuleFactory {
	public ConnectionModule getConnectionModule();
	public ChoiceModule getChoiceModule();
}
