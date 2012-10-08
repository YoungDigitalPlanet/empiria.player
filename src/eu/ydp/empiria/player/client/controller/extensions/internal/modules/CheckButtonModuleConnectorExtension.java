package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.button.CheckButtonModule;

public class CheckButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator() {
			@Override
			public IModule createModule() {
				CheckButtonModule button = new CheckButtonModule();
				initializeModule(button);
				return button;
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.MARK_ALL_BUTTON.tagName();
	}

}
