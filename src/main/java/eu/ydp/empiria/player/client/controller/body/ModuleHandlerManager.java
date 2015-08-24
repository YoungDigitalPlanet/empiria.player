package eu.ydp.empiria.player.client.controller.body;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.IModule;

import java.util.ArrayList;
import java.util.List;

@Singleton
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
