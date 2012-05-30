package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.button.FeedbackAudioMuteButtonModule;

public class AudioMuteButtonModuleConnectorExtension extends
		ControlModuleConnectorExtension {

	@Override
	public ModuleCreator getModuleCreator() {
		return new AbstractModuleCreator() {
			
			@Override
			public IModule createModule() {
				FeedbackAudioMuteButtonModule button = new FeedbackAudioMuteButtonModule();
				initializeModule(button);
				return button;
			}
		};
	}

	@Override
	public String getModuleNodeName() {
		return ModuleTagName.AUDIO_MUTE_BUTTON.tagName();
	}

}
