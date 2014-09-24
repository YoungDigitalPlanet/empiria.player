package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import static eu.ydp.empiria.player.client.util.MapCreator.*;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.NavigationButtonFactory;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.module.button.NavigationButtonModule;

public abstract class NavigationButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

	private static final Map<String, NavigationButtonDirection> NODE2DIRECTION = m(new HashMap<String, NavigationButtonDirection>()).p(
			ModuleTagName.NEXT_ITEM_NAVIGATION.tagName(), NavigationButtonDirection.NEXT).p(ModuleTagName.PREV_ITEM_NAVIGATION.tagName(),
			NavigationButtonDirection.PREVIOUS);

	@Inject
	private NavigationButtonFactory navigationButtonFactory;

	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator() {
			@Override
			public IModule createModule() {
				NavigationButtonDirection direction = getDirection(getModuleNodeName());
				NavigationButtonModule button = navigationButtonFactory.getNavigationButtonModule(direction);
				initializeModule(button);
				return button;
			}
		};
	}

	private NavigationButtonDirection getDirection(String name) {
		return NODE2DIRECTION.get(name);
	}

}
