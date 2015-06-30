package eu.ydp.empiria.player.client.controller.body;

import eu.ydp.empiria.player.client.module.IModule;

public interface ModuleHandler {

    /**
     * Registers module to be handled by the handler.
     *
     * @param module Module to be handled.
     */
    void register(IModule module);
}
