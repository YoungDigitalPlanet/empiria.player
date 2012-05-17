package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.report.ReportModule;

public class ReportModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension, DataSourceDataSocketUserExtension, FlowRequestSocketUserExtension, SessionDataSocketUserExtension {

	protected FlowRequestInvoker flowRequestInvoker;
	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected SessionDataSupplier sessionDataSupplier;

	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator(){
			@Override
			public IModule createModule() {
				return new ReportModule(flowRequestInvoker, dataSourceDataSupplier, sessionDataSupplier);
			}
		};
	}

	@Override
	public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
		flowRequestInvoker = fri;
	}

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		dataSourceDataSupplier = supplier;
	}

	@Override
	public void setSessionDataSupplier(SessionDataSupplier sessionDataSupplier) {
		this.sessionDataSupplier = sessionDataSupplier;
	}

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.REPORT.tagName();
	}

}
