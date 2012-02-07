package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.choice.ChoiceModule;

public class ChoiceModuleConnectorExtension extends ModuleExtension implements
		ModuleConnectorExtension {

	@Override
	public ModuleCreator getModuleCreator() {
		return new ModuleCreator() {
			
			@Override
			public boolean isInteractionModule() {
				return true;
			}
			
			@Override
			public IModule createModule() {
				return new ChoiceModule();
			}

			@Override
			public boolean isInlineModule() {
				return false;
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return "choiceInteraction";
	}

}
