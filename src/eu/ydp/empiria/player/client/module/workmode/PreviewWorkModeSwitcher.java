package eu.ydp.empiria.player.client.module.workmode;

import eu.ydp.empiria.player.client.module.IModule;

public class PreviewWorkModeSwitcher implements WorkModeSwitcher {

	@Override
	public void enable(IModule module) {
		if (module instanceof WorkModePreviewClient) {
			((WorkModePreviewClient) module).enablePreviewMode();
		}
	}
}
