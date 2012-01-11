package eu.ydp.empiria.player.client.module.registry;

import eu.ydp.empiria.player.client.module.IModule;

public interface ModulesRegistrySocket {

	public boolean isModuleSupported(String nodeName);
	
	public boolean isMultiViewModule(String nodeName);
	
	public boolean isInlineModule(String nodeName);
	
	public IModule createModule(String nodeName);
}
