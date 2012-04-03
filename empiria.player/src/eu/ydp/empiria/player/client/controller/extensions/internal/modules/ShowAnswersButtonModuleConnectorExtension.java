package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.button.CheckButtonModule;
import eu.ydp.empiria.player.client.module.button.ShowAnswersButtonModule;

public class ShowAnswersButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

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
				ShowAnswersButtonModule button = new ShowAnswersButtonModule();
				initializeModule(button);
				return button;
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return "showAnswersButton";
	}

}
