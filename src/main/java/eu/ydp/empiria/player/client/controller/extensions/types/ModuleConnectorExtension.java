package eu.ydp.empiria.player.client.controller.extensions.types;

import eu.ydp.empiria.player.client.module.ModuleCreator;

public interface ModuleConnectorExtension {

    /**
     * Generator dla konkretnego modulu
     *
     * @return
     */
    public ModuleCreator getModuleCreator();

    /**
     * nazwa noda modulu w pliku xml
     *
     * @return
     */
    public String getModuleNodeName();
}
