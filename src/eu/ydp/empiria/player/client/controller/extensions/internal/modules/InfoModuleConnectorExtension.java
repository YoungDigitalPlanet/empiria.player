package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.info.InfoModule;

public class InfoModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension, DataSourceDataSocketUserExtension, SessionDataSocketUserExtension, FlowDataSocketUserExtension {

	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected SessionDataSupplier sessionDataSupplier;
	protected FlowDataSupplier flowDataSupplier;

	@Override
	public ModuleCreator getModuleCreator() {
		return new ModuleCreator() {
			
			@Override
			public boolean isInteractionModule() {
				return false;
			}
			
			@Override
			public boolean isInlineModule() {
				return true;
			}
			
			@Override
			public IModule createModule() {
				return new InfoModule(dataSourceDataSupplier, sessionDataSupplier, flowDataSupplier);
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return "info";
	}
	
	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		dataSourceDataSupplier = supplier;
	}

	@Override
	public void setSessionDataSupplier(SessionDataSupplier supplier) {
		sessionDataSupplier = supplier;
	}

	@Override
	public void setFlowDataSupplier(FlowDataSupplier supplier) {
		flowDataSupplier = supplier;
	}

}
