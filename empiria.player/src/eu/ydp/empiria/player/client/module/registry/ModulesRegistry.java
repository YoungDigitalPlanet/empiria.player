package eu.ydp.empiria.player.client.module.registry;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;

public class ModulesRegistry implements ModulesRegistrySocket {

	protected Map<String, ModuleCreator> moduleCreators;
	
	public ModulesRegistry(){
		moduleCreators = new HashMap<String, ModuleCreator>();
	}
	
	public void registerModuleCreator(String nodeName, ModuleCreator creator){
		moduleCreators.put(nodeName, creator);
	}
	
	public boolean isModuleSupported(String nodeName){
		return moduleCreators.keySet().contains(nodeName);
	}

	@Override
	public boolean isMultiViewModule(String nodeName){
		ModuleCreator currCreator = moduleCreators.get(nodeName);
		if (currCreator != null){
			return currCreator.isMultiViewModule();
		}
		return false;
	}

	@Override
	public boolean isInlineModule(String nodeName) {
		ModuleCreator currCreator = moduleCreators.get(nodeName);
		if (currCreator != null){
			return currCreator.isInlineModule();
		}
		return false;
	}
	
	public IModule createModule(String nodeName){
		ModuleCreator currCreator = moduleCreators.get(nodeName);
		if (currCreator != null){
			return currCreator.createModule();
		}
		return null;		
	}
	
	
	
}
