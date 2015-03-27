package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.module.ModuleTagName;

public class PrevPageButtonModuleConnectorExtension extends NavigationButtonModuleConnectorExtension {

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.PREV_ITEM_NAVIGATION.tagName();
	}

}
