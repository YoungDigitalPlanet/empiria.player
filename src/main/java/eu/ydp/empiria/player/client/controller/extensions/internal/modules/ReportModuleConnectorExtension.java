package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.report.ReportModule;

public class ReportModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension {

    @Inject
    private Provider<ReportModule> reportModule;

    @Override
    public ModuleCreator getModuleCreator() {
        return new AbstractModuleCreator() {
            @Override
            public IModule createModule() {
                return reportModule.get();
            }
        };
    }

    @Override
    public String getModuleNodeName() {
        return ModuleTagName.REPORT.tagName();
    }
}
