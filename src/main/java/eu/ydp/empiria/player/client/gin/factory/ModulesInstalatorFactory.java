package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.ModulesInstalator;
import eu.ydp.empiria.player.client.controller.body.parenthood.ParenthoodGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;

public interface ModulesInstalatorFactory {
    ModulesInstalator createModulesInstalator(ParenthoodGeneratorSocket pts, ModulesRegistrySocket reg, ModuleSocket ms);
}
