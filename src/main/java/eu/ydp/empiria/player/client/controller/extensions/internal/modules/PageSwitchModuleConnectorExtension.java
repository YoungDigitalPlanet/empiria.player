package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.pageswitch.PageSwitchModule;

public class PageSwitchModuleConnectorExtension extends ControlModuleConnectorExtension {

	@Inject
	private ModuleFactory moduleFactory;
	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator() {
			@Override
			public IModule createModule() {
				PageSwitchModule pagesSwitch = moduleFactory.createPageSwitchModule();
				initializeModule(pagesSwitch);
				return pagesSwitch;
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.PAGES_SWITCH_BOX.tagName();
	}
}