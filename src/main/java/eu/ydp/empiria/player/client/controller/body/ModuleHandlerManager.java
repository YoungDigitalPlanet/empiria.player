package eu.ydp.empiria.player.client.controller.body;

import eu.ydp.empiria.player.client.module.IModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleHandlerManager {

    private final List<ModuleHandler> handlers = new ArrayList<ModuleHandler>();

    public void addModuleHandler(ModuleHandler handler) {
        handlers.add(handler);
    }

    public void registerModule(IModule module) {
        for (ModuleHandler handler : handlers) {
            handler.register(module);
        }
    }
}
