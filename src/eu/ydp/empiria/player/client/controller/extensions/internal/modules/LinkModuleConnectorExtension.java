package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.gin.factory.LinkModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;

public class LinkModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension, FlowRequestSocketUserExtension {

	@Inject
	private LinkModuleFactory moduleFactory;

	protected FlowRequestInvoker flowRequestInvoker;

	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator() {
			@Override
			public IModule createModule() {
				return moduleFactory.getLinkModule(flowRequestInvoker);
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.LINK.tagName();
	}

	@Override
	public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
		flowRequestInvoker = fri;
	}

}
