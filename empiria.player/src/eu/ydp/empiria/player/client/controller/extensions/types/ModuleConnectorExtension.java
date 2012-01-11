package eu.ydp.empiria.player.client.controller.extensions.types;

import eu.ydp.empiria.player.client.module.ModuleCreator;

public interface ModuleConnectorExtension {

	public ModuleCreator getModuleCreator();
	
	public String getModuleNodeName();
}
