package eu.ydp.empiria.player.client.controller.body;

import java.util.ArrayList;
import java.util.List;

import eu.ydp.empiria.player.client.module.IModule;

public class ModuleHandlerManager implements ModuleHandlerRegistrar {

	private List<ModuleHandler> handlers = new ArrayList<ModuleHandler>();

	public void addModuleHandler(ModuleHandler handler){
		handlers.add(handler);
	}
	
	public void registerModule(IModule module){
		for (ModuleHandler handler : handlers){
			handler.register(module);
		}
	}
}
