package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.test.navigation.PagesSwitch;


public class PagesSwitchExtension extends ControllModuleExtension{
	
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
				PagesSwitch pagesSwitch = new PagesSwitch();
				
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