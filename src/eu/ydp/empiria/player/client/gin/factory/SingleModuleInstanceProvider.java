package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.modules.LinkModuleConnectorExtension;
import eu.ydp.empiria.player.client.inject.Instance;

public class SingleModuleInstanceProvider {
	@Inject
	private Instance<LinkModuleConnectorExtension> linkModuleConnectorExtension;

	public LinkModuleConnectorExtension getLinkModuleConnectorExtension() {
		return linkModuleConnectorExtension.get();
	}
}
