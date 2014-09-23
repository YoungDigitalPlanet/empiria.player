package eu.ydp.empiria.player.client.module.workmode;

import eu.ydp.empiria.player.client.module.IModule;

public class TestSubmittedWorkModeSwitcher implements WorkModeSwitcher {

	@Override
	public void enable(IModule module) {
		if (module instanceof WorkModeTestSubmittedClient) {
			((WorkModeTestSubmittedClient) module).enableTestSubmittedMode();
		}
	}

	@Override
	public void disable(IModule module) {
		if (module instanceof WorkModeTestSubmittedClient) {
			((WorkModeTestSubmittedClient) module).disableTestSubmittedMode();
		}
	}
}
