package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.pageswitch.PageSwitchModule;


public class PageSwitchModuleConnectorExtension extends ControlModuleConnectorExtension{
	
	@Override
	public ModuleCreator getModuleCreator() {
		return new ModuleCreator() {
			
			@Override
			public boolean isMultiViewModule() {
				return false;
			}
			
			@Override
			public boolean isInlineModule() {
				return false;
			}
			
			@Override
			public IModule createModule() {
				PageSwitchModule pagesSwitch = new PageSwitchModule();
				
				initializeModule(pagesSwitch);
				
				return pagesSwitch;
			}
		};
	}
	
	@Override
	public String getModuleNodeName() {
		return "pagesSwitchBox";
	}
}