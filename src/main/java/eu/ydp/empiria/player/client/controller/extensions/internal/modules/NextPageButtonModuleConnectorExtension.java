package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.module.ModuleTagName;

public class NextPageButtonModuleConnectorExtension extends NavigationButtonModuleConnectorExtension {

    @Override
    public String getModuleNodeName() {
        return ModuleTagName.NEXT_ITEM_NAVIGATION.tagName();
    }

}
