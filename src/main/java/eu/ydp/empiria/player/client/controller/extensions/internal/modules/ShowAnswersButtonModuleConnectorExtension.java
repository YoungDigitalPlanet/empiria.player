package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.button.ShowAnswersButtonModule;

public class ShowAnswersButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

	@Inject
	Provider<ShowAnswersButtonModule> provider;

	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator() {
			@Override
			public IModule createModule() {
				ShowAnswersButtonModule button = provider.get();
				initializeModule(button);
				return button;
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.SHOW_ANSWERS_BUTTON.tagName();
	}

}
