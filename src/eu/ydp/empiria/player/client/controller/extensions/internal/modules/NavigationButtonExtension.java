package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import static eu.ydp.empiria.player.client.util.MapCreator.m;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.test.navigation.NavigationButton;
import eu.ydp.empiria.player.client.module.test.navigation.NavigationButtonDirection;

public abstract class NavigationButtonExtension extends ControllModuleExtension{
	
	private static final Map<String, NavigationButtonDirection> NODE2DIRECTION = m(new HashMap<String, NavigationButtonDirection>()).
			p("nextItemNavigation", NavigationButtonDirection.NEXT).
			p("prevItemNavigation", NavigationButtonDirection.PREVIOUS);
	
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
				NavigationButtonDirection direction =  getDirection(getModuleNodeName());
				NavigationButton button = new NavigationButton(direction);
				
				initializeModule(button);
				
				return button;
			}
		};
	}
	
	private NavigationButtonDirection getDirection(String name){
		return NODE2DIRECTION.get(name);
	}
	
}
