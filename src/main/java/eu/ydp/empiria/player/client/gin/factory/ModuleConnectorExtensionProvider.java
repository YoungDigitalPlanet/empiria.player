package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.NextPageButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PageSwitchModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PrevPageButtonModuleConnectorExtension;

public class ModuleConnectorExtensionProvider {

    @Inject
    private PrevPageButtonModuleConnectorExtension prevPageButtonModuleConnectorExtension;
    @Inject
    private NextPageButtonModuleConnectorExtension nextPageButtonModuleConnectorExtension;
    @Inject
    private PageSwitchModuleConnectorExtension pageSwitchModuleConnectorExtension;

    public PrevPageButtonModuleConnectorExtension getPrevPageButtonModuleConnectorExtension() {
        return prevPageButtonModuleConnectorExtension;
    }

    public NextPageButtonModuleConnectorExtension getNextPageButtonModuleConnectorExtension() {
        return nextPageButtonModuleConnectorExtension;
    }

    public PageSwitchModuleConnectorExtension getPageSwitchModuleConnectorExtension() {
        return pageSwitchModuleConnectorExtension;
    }
}
